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
package net.sourceforge.plantuml.svek.image;

import net.sourceforge.plantuml.awt.geom.Dimension2D;

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.SkinParamUtils;
import net.sourceforge.plantuml.UseStyle;
import net.sourceforge.plantuml.cucadiagram.ILeaf;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.style.PName;
import net.sourceforge.plantuml.style.SName;
import net.sourceforge.plantuml.style.Style;
import net.sourceforge.plantuml.style.StyleSignatureBasic;
import net.sourceforge.plantuml.svek.AbstractEntityImage;
import net.sourceforge.plantuml.svek.ShapeType;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UPolygon;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.color.HColor;

public class EntityImageAssociation extends AbstractEntityImage {

	final private static int SIZE = 12;
	private final SName sname;

	public EntityImageAssociation(ILeaf entity, ISkinParam skinParam, SName sname) {
		super(entity, skinParam);
		this.sname = sname;
	}

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		return new Dimension2DDouble(SIZE * 2, SIZE * 2);
	}

	private Style getStyle() {
		return getStyleSignature().getMergedStyle(getSkinParam().getCurrentStyleBuilder());
	}

	private StyleSignatureBasic getStyleSignature() {
		return StyleSignatureBasic.of(SName.root, SName.element, sname, SName.diamond);
	}

	final public void drawU(UGraphic ug) {
		final UPolygon diams = new UPolygon();

		diams.addPoint(SIZE, 0);
		diams.addPoint(SIZE * 2, SIZE);
		diams.addPoint(SIZE, SIZE * 2);
		diams.addPoint(0, SIZE);
		diams.addPoint(SIZE, 0);

		final HColor borderColor;
		final HColor backgroundColor;
		double shadow = 0;
		UStroke stroke = new UStroke(1.5);
		if (UseStyle.useBetaStyle()) {
			final Style style = getStyle();
			borderColor = style.value(PName.LineColor).asColor(getSkinParam().getThemeStyle(),
					getSkinParam().getIHtmlColorSet());
			backgroundColor = style.value(PName.BackGroundColor).asColor(getSkinParam().getThemeStyle(),
					getSkinParam().getIHtmlColorSet());
			shadow = style.value(PName.Shadowing).asDouble();
			stroke = style.getStroke();
		} else {
			borderColor = SkinParamUtils.getColor(getSkinParam(), getStereo(), ColorParam.classBorder);
			backgroundColor = SkinParamUtils.getColor(getSkinParam(), getStereo(), ColorParam.classBackground);
			if (getSkinParam().shadowing(getEntity().getStereotype()))
				shadow = 5;
		}

		diams.setDeltaShadow(shadow);
		ug.apply(borderColor).apply(backgroundColor.bg()).apply(stroke).draw(diams);
	}

	public ShapeType getShapeType() {
		return ShapeType.DIAMOND;
	}

}
