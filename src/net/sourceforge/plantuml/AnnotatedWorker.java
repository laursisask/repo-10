/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2023, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
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
 *
 */
package net.sourceforge.plantuml;

import java.awt.geom.Rectangle2D;

import net.sourceforge.plantuml.activitydiagram3.ftile.EntityImageLegend;
import net.sourceforge.plantuml.awt.geom.Dimension2D;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.cucadiagram.DisplayPositioned;
import net.sourceforge.plantuml.cucadiagram.DisplaySection;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.InnerStrategy;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.SymbolContext;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.graphic.USymbols;
import net.sourceforge.plantuml.style.PName;
import net.sourceforge.plantuml.style.SName;
import net.sourceforge.plantuml.style.Style;
import net.sourceforge.plantuml.style.StyleSignatureBasic;
import net.sourceforge.plantuml.svek.DecorateEntityImage;
import net.sourceforge.plantuml.svek.TextBlockBackcolored;
import net.sourceforge.plantuml.ugraphic.MinMax;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.color.HColor;

public class AnnotatedWorker {

	private final Annotated annotated;
	private final ISkinParam skinParam;
	private final StringBounder stringBounder;

	public AnnotatedWorker(Annotated annotated, ISkinParam skinParam, StringBounder stringBounder) {
		this.annotated = annotated;
		this.skinParam = skinParam;
		this.stringBounder = stringBounder;
	}

	public TextBlockBackcolored addAdd(TextBlock result) {
		result = addFrame(result);
		result = addLegend(result);
		result = addTitle(result);
		result = addCaption(result);
		result = addHeaderAndFooter(result);
		return (TextBlockBackcolored) result;
	}

	public boolean hasMainFrame() {
		return annotated.getMainFrame() != null;
	}

	public TextBlock addFrame(final TextBlock original) {
		final Display mainFrame = annotated.getMainFrame();
		if (mainFrame == null)
			return original;

		final double x1 = 5;
		final double x2 = 7;
		final double y1 = 10;
		final double y2 = 10;

		final Style style = StyleSignatureBasic.of(SName.root, SName.document, SName.frame)
				.getMergedStyle(skinParam.getCurrentStyleBuilder());
		final double deltaShadow = style.value(PName.Shadowing).asDouble();
		final FontConfiguration fontConfiguration = FontConfiguration.create(getSkinParam(), style);
		final UStroke stroke = style.getStroke();
		final HColor borderColor = style.value(PName.LineColor).asColor(skinParam.getThemeStyle(),
				skinParam.getIHtmlColorSet());

		final SymbolContext symbolContext = new SymbolContext(getBackgroundColor(), borderColor).withShadow(deltaShadow)
				.withStroke(stroke);
		final MinMax originalMinMax = TextBlockUtils.getMinMax(original, stringBounder, false);

		final TextBlock title = mainFrame.create(fontConfiguration, HorizontalAlignment.CENTER, getSkinParam());
		final Dimension2D dimTitle = title.calculateDimension(stringBounder);

		final double width = x1 + Math.max(originalMinMax.getWidth(), dimTitle.getWidth()) + x2;
		final double height = dimTitle.getHeight() + y1 + originalMinMax.getHeight() + y2;
		final TextBlock frame = USymbols.FRAME.asBig(title, HorizontalAlignment.LEFT, TextBlockUtils.empty(0, 0), width,
				height, symbolContext, skinParam.getStereotypeAlignment());

		return new TextBlockBackcolored() {

			public void drawU(UGraphic ug) {
				frame.drawU(ug.apply(UTranslate.dx(originalMinMax.getMinX())));
				original.drawU(ug.apply(new UTranslate(x1, y1 + dimTitle.getHeight())));
				// original.drawU(ug);
			}

			public MinMax getMinMax(StringBounder stringBounder) {
				return TextBlockUtils.getMinMax(this, stringBounder, false);
			}

			public Rectangle2D getInnerPosition(String member, StringBounder stringBounder, InnerStrategy strategy) {
				final Rectangle2D rect = original.getInnerPosition(member, stringBounder, strategy);
				return new Rectangle2D.Double(rect.getX() + x1, rect.getY() + y1 + dimTitle.getHeight(),
						rect.getWidth(), rect.getHeight());
			}

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				return original.calculateDimension(stringBounder);
			}

			public HColor getBackcolor() {
				return symbolContext.getBackColor();
			}
		};
	}

	private HColor getBackgroundColor() {
		return getSkinParam().getBackgroundColor();
	}

	private TextBlock addLegend(TextBlock original) {
		final DisplayPositioned legend = annotated.getLegend();
		if (legend.isNull())
			return original;

		final TextBlock text = EntityImageLegend.create(legend.getDisplay(), getSkinParam());

		return DecorateEntityImage.add(original, text, legend.getHorizontalAlignment(), legend.getVerticalAlignment());
	}

	private ISkinParam getSkinParam() {
		return skinParam;
	}

	private TextBlock addCaption(TextBlock original) {
		final DisplayPositioned caption = annotated.getCaption();
		if (caption.isNull())
			return original;

		final TextBlock text = getCaption();
		return DecorateEntityImage.addBottom(original, text, HorizontalAlignment.CENTER);
	}

	public TextBlock getCaption() {
		final DisplayPositioned caption = annotated.getCaption();
		if (caption.isNull())
			return TextBlockUtils.empty(0, 0);

		final Style style = StyleSignatureBasic.of(SName.root, SName.document, SName.caption)
				.getMergedStyle(skinParam.getCurrentStyleBuilder());
		return style.createTextBlockBordered(caption.getDisplay(), skinParam.getIHtmlColorSet(), skinParam);

	}

	private TextBlock addTitle(TextBlock original) {
		final DisplayPositioned title = (DisplayPositioned) annotated.getTitle();
		if (title.isNull())
			return original;

		final Style style = StyleSignatureBasic.of(SName.root, SName.document, SName.title)
				.getMergedStyle(skinParam.getCurrentStyleBuilder());
		final TextBlock block = style.createTextBlockBordered(title.getDisplay(), skinParam.getIHtmlColorSet(),
				skinParam);

		return DecorateEntityImage.addTop(original, block, HorizontalAlignment.CENTER);
	}

	private TextBlock addHeaderAndFooter(TextBlock original) {
		final DisplaySection footer = annotated.getFooter();
		final DisplaySection header = annotated.getHeader();
		if (footer.isNull() && header.isNull())
			return original;

		TextBlock textFooter = null;
		if (footer.isNull() == false) {
			final Style style = StyleSignatureBasic.of(SName.root, SName.document, SName.footer)
					.getMergedStyle(skinParam.getCurrentStyleBuilder());
			textFooter = footer.createRibbon(FontConfiguration.create(getSkinParam(), FontParam.FOOTER, null),
					getSkinParam(), style);
		}
		TextBlock textHeader = null;
		if (header.isNull() == false) {
			final Style style = StyleSignatureBasic.of(SName.root, SName.document, SName.header)
					.getMergedStyle(skinParam.getCurrentStyleBuilder());
			textHeader = header.createRibbon(FontConfiguration.create(getSkinParam(), FontParam.HEADER, null),
					getSkinParam(), style);
		}

		return DecorateEntityImage.addTopAndBottom(original, textHeader, header.getHorizontalAlignment(), textFooter,
				footer.getHorizontalAlignment());
	}
}
