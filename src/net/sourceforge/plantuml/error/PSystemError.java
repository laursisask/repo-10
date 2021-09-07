/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 *
 */
package net.sourceforge.plantuml.error;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sourceforge.plantuml.BackSlash;
import net.sourceforge.plantuml.ErrorUml;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.FileImageData;
import net.sourceforge.plantuml.LineLocation;
import net.sourceforge.plantuml.PlainDiagram;
import net.sourceforge.plantuml.SpriteContainerEmpty;
import net.sourceforge.plantuml.StringLocated;
import net.sourceforge.plantuml.api.ImageDataSimple;
import net.sourceforge.plantuml.asciiart.UmlCharArea;
import net.sourceforge.plantuml.core.DiagramDescription;
import net.sourceforge.plantuml.core.ImageData;
import net.sourceforge.plantuml.core.UmlSource;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.flashcode.FlashCodeFactory;
import net.sourceforge.plantuml.flashcode.FlashCodeUtils;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.GraphicStrings;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockRaw;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.graphic.VerticalAlignment;
import net.sourceforge.plantuml.security.SecurityUtils;
import net.sourceforge.plantuml.svek.GraphvizCrash;
import net.sourceforge.plantuml.svek.TextBlockBackcolored;
import net.sourceforge.plantuml.ugraphic.AffineTransformType;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;
import net.sourceforge.plantuml.ugraphic.MinMax;
import net.sourceforge.plantuml.ugraphic.PixelImage;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UImage;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.color.HColor;
import net.sourceforge.plantuml.ugraphic.color.HColorSet;
import net.sourceforge.plantuml.ugraphic.color.HColorSimple;
import net.sourceforge.plantuml.ugraphic.color.HColorUtils;
import net.sourceforge.plantuml.ugraphic.txt.UGraphicTxt;
import net.sourceforge.plantuml.version.PSystemVersion;
import net.sourceforge.plantuml.version.Version;

public abstract class PSystemError extends PlainDiagram {

	protected List<StringLocated> trace;
	protected ErrorUml singleError;

	public PSystemError(UmlSource source) {
		super(source);
	}

	@Override
	public ImageBuilder createImageBuilder(FileFormatOption fileFormatOption) throws IOException {
		return super.createImageBuilder(fileFormatOption).blackBackcolor().randomPixel().status(FileImageData.ERROR);
	}

	final protected StringLocated getLastLine() {
		return trace.get(trace.size() - 1);
	}

	final public LineLocation getLineLocation() {
		return getLastLine().getLocation();
	}

	final public Collection<ErrorUml> getErrorsUml() {
		return Collections.singleton(singleError);
	}

	final public ErrorUml getFirstError() {
		return singleError;
	}

	final public String getWarningOrError() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getDescription());
		sb.append(BackSlash.CHAR_NEWLINE);
		for (CharSequence t : getTitle().getDisplay()) {
			sb.append(t);
			sb.append(BackSlash.CHAR_NEWLINE);
		}
		sb.append(BackSlash.CHAR_NEWLINE);
		return sb.toString();
	}

	private TextBlockBackcolored getGraphicalFormatted() {
		final FontConfiguration fc0 = GraphicStrings.sansSerif14(HColorUtils.BLACK).bold();
		final FontConfiguration fc1 = GraphicStrings.sansSerif14(HColorUtils.MY_GREEN).bold();
		final FontConfiguration fc2 = GraphicStrings.sansSerif14(HColorUtils.RED).bold();
		final FontConfiguration fc4 = GraphicStrings.sansSerif12(HColorUtils.MY_GREEN).bold().italic();

		final List<String> fullBody = getTextFullBody();
		final TextBlock result0 = TextBlockUtils.addBackcolor(
				TextBlockUtils.withMargin(new TextBlockRaw(getTextFromStack(), fc0), 1, 1, 1, 4), HColorUtils.MY_GREEN);
		final TextBlock result1 = new TextBlockRaw(allButLast(fullBody), fc1);
		final TextBlock result2 = new TextBlockRaw(onlyLast(fullBody), fc1.wave(HColorUtils.RED));
		final TextBlock result3 = new TextBlockRaw(getTextError(), fc2);
		final TextBlock result4 = TextBlockUtils.withMargin(new TextBlockRaw(header(), fc4), 0, 2, 0, 8);
		TextBlock result = result0;
		result = TextBlockUtils.mergeTB(result, result1, HorizontalAlignment.LEFT);
		result = TextBlockUtils.mergeTB(result, result2, HorizontalAlignment.LEFT);
		result = TextBlockUtils.mergeTB(result, result3, HorizontalAlignment.LEFT);
		result = TextBlockUtils.mergeTB(result4, result, HorizontalAlignment.LEFT);
		result = TextBlockUtils.withMargin(result, 5, 5);
		return TextBlockUtils.addBackcolor(result, HColorUtils.BLACK);
	}

	private List<String> header() {
		final List<String> result = new ArrayList<>();
		result.add("PlantUML " + Version.versionString());
		GraphvizCrash.checkOldVersionWarning(result);
		return result;
	}

	public List<String> getPureAsciiFormatted() {
		final List<String> result = getTextFromStack();
		result.addAll(getTextFullBody());
		result.add("^^^^^");
		result.addAll(getTextError());
		return result;
	}

	private List<String> getTextFromStack() {
		LineLocation lineLocation = getLineLocation();
		final List<String> result = new ArrayList<>();
		if (lineLocation != null) {
			append(result, lineLocation);
			while (lineLocation.getParent() != null) {
				lineLocation = lineLocation.getParent();
				append(result, lineLocation);
			}
		}
		return result;
	}

	protected List<String> getTextFullBody() {
		final List<String> result = new ArrayList<>();
		result.add(" ");
		final int traceSize = trace.size();
		if (traceSize > 40) {
			for (StringLocated s : trace.subList(0, 5)) {
				addToResult(result, s);
			}
			result.add("...");
			final int skipped = traceSize - 5 - 20;
			result.add("... ( skipping " + skipped + " lines )");
			result.add("...");
			for (StringLocated s : trace.subList(traceSize - 20, traceSize)) {
				addToResult(result, s);
			}
		} else {
			for (StringLocated s : trace) {
				addToResult(result, s);
			}
		}
		return result;
	}

	private void addToResult(final List<String> result, StringLocated s) {
		String tmp = s.getString();
		if (tmp.length() > 120) {
			tmp = tmp.substring(0, 120) + " ...";
		}
		result.add(tmp);
	}

	private List<String> getTextError() {
		return Arrays.asList(" " + singleError.getError());
	}

	@Override
	final protected ImageData exportDiagramNow(OutputStream os, int num, FileFormatOption fileFormat)
			throws IOException {
		if (fileFormat.getFileFormat() == FileFormat.ATXT || fileFormat.getFileFormat() == FileFormat.UTXT) {
			final UGraphicTxt ugt = new UGraphicTxt();
			final UmlCharArea area = ugt.getCharArea();
			area.drawStringsLRSimple(getPureAsciiFormatted(), 0, 0);
			area.print(SecurityUtils.createPrintStream(os));
			return new ImageDataSimple(1, 1);

		}
		return super.exportDiagramNow(os, num, fileFormat);
	}

	@Override
	protected UDrawable getRootDrawable(FileFormatOption fileFormatOption) throws IOException {
		final TextBlockBackcolored result = getGraphicalFormatted();

		TextBlock udrawable = result;
		
		return udrawable;
	}

	private void append(List<String> result, LineLocation lineLocation) {
		if (lineLocation.getDescription() != null) {
			result.add("[From " + lineLocation.getDescription() + " (line " + (lineLocation.getPosition() + 1) + ") ]");
		}
	}

	final public DiagramDescription getDescription() {
		return new DiagramDescription("(Error)");
	}

	private List<String> allButLast(List<String> full) {
		return full.subList(0, full.size() - 1);
	}

	private List<String> onlyLast(List<String> full) {
		return full.subList(full.size() - 1, full.size());
	}

	public int size() {
		return trace.size();
	}

	public int score() {
		final int result = trace.size() * 10 + singleError.score();
		return result;
	}

}
