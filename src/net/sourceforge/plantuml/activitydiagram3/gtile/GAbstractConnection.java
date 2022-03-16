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
 *
 */
package net.sourceforge.plantuml.activitydiagram3.gtile;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.UseStyle;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.graphic.Rainbow;
import net.sourceforge.plantuml.style.SName;
import net.sourceforge.plantuml.style.Style;
import net.sourceforge.plantuml.style.StyleSignatureBasic;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public abstract class GAbstractConnection implements GConnection {

	protected final GPoint gpoint1;
	protected final GPoint gpoint2;

	public GAbstractConnection(GPoint gpoint1, GPoint gpoint2) {
		this.gpoint1 = gpoint1;
		this.gpoint2 = gpoint2;
	}

	@Override
	public String toString() {
		return "[" + gpoint1 + "]->[" + gpoint2 + "]";
	}

	@Override
	final public List<GPoint> getHooks() {
		return Arrays.asList(gpoint1, gpoint2);
	}

	@Override
	final public void drawTranslatable(UGraphic ug) {
		final Swimlane swimlane1 = gpoint1.getSwimlane();
		final Swimlane swimlane2 = gpoint2.getSwimlane();

		if (swimlane1 == swimlane2)
			return;

		final UTranslate translate1 = swimlane1.getTranslate();
		final UTranslate translate2 = swimlane2.getTranslate();

		drawTranslate(ug, translate1, translate2);

	}

	public void drawTranslate(UGraphic ug, UTranslate translate1, UTranslate translate2) {
		throw new UnsupportedOperationException();
	}
	
	private final StyleSignatureBasic getDefaultStyleDefinitionArrow() {
		return StyleSignatureBasic.of(SName.root, SName.element, SName.activityDiagram, SName.arrow);
	}
	
	protected ISkinParam skinParam() {
		throw new UnsupportedOperationException("wip");
	}
	
	// DUPLICATE 4561
	final protected Rainbow getInLinkRenderingColor() {
		Rainbow color;
		final ISkinParam skinParam = gpoint1.getGtile().skinParam();
		if (UseStyle.useBetaStyle()) {
			final Style style = getDefaultStyleDefinitionArrow().getMergedStyle(skinParam.getCurrentStyleBuilder());
			color = Rainbow.build(style, skinParam.getIHtmlColorSet(), skinParam.getThemeStyle());
		} else
			color = Rainbow.build(skinParam);
//		final LinkRendering linkRendering = tile.getInLinkRendering();
//		if (linkRendering == null) {
//			if (UseStyle.useBetaStyle()) {
//				final Style style = getDefaultStyleDefinitionArrow()
//						.getMergedStyle(skinParam().getCurrentStyleBuilder());
//				return Rainbow.build(style, skinParam().getIHtmlColorSet(), skinParam().getThemeStyle());
//			} else {
//				color = Rainbow.build(skinParam());
//			}
//		} else {
//			color = linkRendering.getRainbow();
//		}
//		if (color.size() == 0) {
//			if (UseStyle.useBetaStyle()) {
//				final Style style = getDefaultStyleDefinitionArrow()
//						.getMergedStyle(skinParam().getCurrentStyleBuilder());
//				return Rainbow.build(style, skinParam().getIHtmlColorSet(), skinParam().getThemeStyle());
//			} else {
//				color = Rainbow.build(skinParam());
//			}
//		}
		return color;
	}




}
