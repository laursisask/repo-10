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
package net.sourceforge.plantuml.ugraphic.svg;

import java.awt.geom.Rectangle2D;

import net.sourceforge.plantuml.svg.SvgGraphics;
import net.sourceforge.plantuml.ugraphic.ClipContainer;
import net.sourceforge.plantuml.ugraphic.UClip;
import net.sourceforge.plantuml.ugraphic.UDriver;
import net.sourceforge.plantuml.ugraphic.UParam;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.color.ColorMapper;
import net.sourceforge.plantuml.ugraphic.color.HColor;
import net.sourceforge.plantuml.ugraphic.color.HColorGradient;

public class DriverRectangleSvg implements UDriver<URectangle, SvgGraphics> {

	private final ClipContainer clipContainer;

	public DriverRectangleSvg(ClipContainer clipContainer) {
		this.clipContainer = clipContainer;
	}

	public void draw(URectangle rect, double x, double y, ColorMapper mapper, UParam param, SvgGraphics svg) {
		final double rx = rect.getRx();
		final double ry = rect.getRy();
		double width = rect.getWidth();
		double height = rect.getHeight();

		applyFillColor(svg, mapper, param);
		applyStrokeColor(svg, mapper, param);

		svg.setStrokeWidth(param.getStroke().getThickness(), param.getStroke().getDasharraySvg());

		final UClip clip = clipContainer.getClip();
		if (clip != null) {
			final Rectangle2D.Double r = clip.getClippedRectangle(new Rectangle2D.Double(x, y, width, height));
			x = r.x;
			y = r.y;
			width = r.width;
			height = r.height;
			if (height <= 0) {
				return;
			}
		}
		svg.svgRectangle(x, y, width, height, rx / 2, ry / 2, rect.getDeltaShadow(), rect.getComment(),
				rect.getCodeLine());
	}

	public static void applyFillColor(SvgGraphics svg, ColorMapper mapper, UParam param) {
		final HColor background = param.getBackcolor();
		if (background instanceof HColorGradient) {
			final HColorGradient gr = (HColorGradient) background;
			final String id = svg.createSvgGradient(mapper.toRGB(gr.getColor1()), mapper.toRGB(gr.getColor2()),
					gr.getPolicy());
			svg.setFillColor("url(#" + id + ")");
		} else {
			final HColor dark = background == null ? null : background.darkSchemeTheme();
			if (dark == background)
				svg.setFillColor(mapper.toSvg(background));
			else
				svg.setFillColor(mapper.toSvg(background), mapper.toSvg(dark));
		}
	}

	public static void applyStrokeColor(SvgGraphics svg, ColorMapper mapper, UParam param) {
		final HColor color = param.getColor();
		if (color instanceof HColorGradient) {
			final HColorGradient gr = (HColorGradient) color;
			final String id = svg.createSvgGradient(mapper.toRGB(gr.getColor1()), mapper.toRGB(gr.getColor2()),
					gr.getPolicy());
			svg.setStrokeColor("url(#" + id + ")");
		} else {
			final HColor dark = color == null ? null : color.darkSchemeTheme();
			if (dark == color)
				svg.setStrokeColor(mapper.toSvg(color));
			else
				svg.setStrokeColor(mapper.toSvg(color), mapper.toSvg(dark));
		}

	}
}
