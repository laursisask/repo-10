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
package net.sourceforge.plantuml.graphic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.api.ThemeStyle;
import net.sourceforge.plantuml.style.PName;
import net.sourceforge.plantuml.style.Style;
import net.sourceforge.plantuml.style.Value;
import net.sourceforge.plantuml.style.ValueNull;
import net.sourceforge.plantuml.ugraphic.color.ColorUtils;
import net.sourceforge.plantuml.ugraphic.color.HColor;
import net.sourceforge.plantuml.ugraphic.color.HColorSet;
import net.sourceforge.plantuml.ugraphic.color.HColorUtils;
import net.sourceforge.plantuml.ugraphic.color.NoSuchColorException;

public class Rainbow {

	private final List<HtmlColorAndStyle> colors = new ArrayList<>();
	private final int colorArrowSeparationSpace;

	private Rainbow(int colorArrowSeparationSpace) {
		this.colorArrowSeparationSpace = colorArrowSeparationSpace;
	}

	@Override
	public String toString() {
		return colors.toString();
	}

	public static Rainbow none() {
		return new Rainbow(0);
	}

	public static Rainbow fromColor(HColor arrowColor, HColor arrowHeadColor) {
		if (arrowColor == null)
			return Rainbow.none();

		return Rainbow.build(new HtmlColorAndStyle(arrowColor, arrowHeadColor));
	}

	@Deprecated
	public static Rainbow build(ISkinParam skinParam) {
		throw new IllegalStateException();
	}

	public static Rainbow build(Style style, HColorSet set, ThemeStyle themeStyle) {
		final HColor color = style.value(PName.LineColor).asColor(themeStyle, set);
		final Value head = style.value(PName.HeadColor);
		HColor colorHead;
		if (head instanceof ValueNull)
			colorHead = color;
		else
			colorHead = head.asColor(themeStyle, set);
		if (colorHead == null)
			colorHead = HColorUtils.transparent();
		return fromColor(color, colorHead);
	}

	public Rainbow withDefault(Rainbow defaultColor) {
		if (this.size() == 0)
			return defaultColor;

		return this;
	}

	public static Rainbow build(HtmlColorAndStyle color) {
		final Rainbow result = new Rainbow(0);
		result.colors.add(Objects.requireNonNull(color));
		return result;
	}

	public static Rainbow build(ISkinParam skinParam, String colorString, int colorArrowSeparationSpace)
			throws NoSuchColorException {
		if (colorString == null)
			return Rainbow.none();

		final Rainbow result = new Rainbow(colorArrowSeparationSpace);
		for (String s : colorString.split(";"))
			result.colors.add(HtmlColorAndStyle.build(skinParam, s));

		return result;
	}

	public boolean isInvisible() {
		for (HtmlColorAndStyle style : colors)
			if (style.getStyle().isInvisible())
				return true;

		return false;
	}

	public List<HtmlColorAndStyle> getColors() {
		return Collections.unmodifiableList(colors);
	}

	public HColor getColor() {
		return colors.get(0).getArrowColor();
	}

	public int getColorArrowSeparationSpace() {
		return colorArrowSeparationSpace;
	}

	public int size() {
		return colors.size();
	}

}
