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
 */
package net.sourceforge.plantuml.ugraphic.color;

public class HColorAutomatic extends HColorAbstract implements HColor {

	private final HColor colorForLight;
	private final HColor colorForDark;
	private final HColor colorForTransparent;

	public HColorAutomatic(HColor colorForLight, HColor colorForDark, HColor colorForTransparent) {
		this.colorForLight = colorForLight;
		this.colorForDark = colorForDark;
		this.colorForTransparent = colorForTransparent;
	}

	public HColor getAppropriateColor(HColor back) {
		if (back == null || HColorUtils.isTransparent(back)) {
			if (colorForTransparent != null)
				return colorForTransparent;

			return ((HColorSimple) colorForLight).withDark(colorForDark);

		}
		if (back.isDark())
			return colorForDark;

		return colorForLight;
	}

}
