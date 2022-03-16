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
package net.sourceforge.plantuml.ugraphic.color;

import java.awt.Color;
import java.util.Objects;

public class ColorMapperTransparentWrapper extends AbstractColorMapper implements ColorMapper {

	private final ColorMapper mapper;

	public ColorMapperTransparentWrapper(ColorMapper mapper) {
		this.mapper = Objects.requireNonNull(mapper);
	}

	public Color toColor(HColor color) {
		if (color == null) {
			return null;
		}
		if (color instanceof HColorBackground) {
			final HColor back = ((HColorBackground) color).getBack();
			return mapper.toColor(back);
		}
		return mapper.toColor(color);
	}

}
