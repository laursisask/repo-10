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
package net.sourceforge.plantuml.nwdiag.core;

import java.awt.geom.Dimension2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.nwdiag.next.LinkedElementNext;
import net.sourceforge.plantuml.nwdiag.next.NBox;
import net.sourceforge.plantuml.ugraphic.MinMax;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.color.HColor;
import net.sourceforge.plantuml.ugraphic.color.HColorSet;
import net.sourceforge.plantuml.ugraphic.color.HColorUtils;

public class NwGroup {

	public static final HColorSet colors = HColorSet.instance();

	private final Set<String> names = new HashSet<>();

	private final String name;
	private HColor color;
	private String description;
	private NBox nbox;

	public NBox getNboxInternal() {
		if (nbox == null) {
			nbox = new NBox();
		}
		return nbox;
	}

	public final NBox getNbox(Map<String, ? extends NServer> servers) {
		if (nbox == null) {
			nbox = new NBox();
			for (Entry<String, ? extends NServer> ent : servers.entrySet()) {
				if (names.contains(ent.getKey())) {
					nbox.add(ent.getValue().getBar());
				}
			}
		}
		return nbox;
	}

	public void addName(String name) {
		this.names.add(name);
	}

	@Override
	public String toString() {
		return "NwGroup:" + name + " " + names + " " + nbox;
	}

	public NwGroup(String name, Object... unused) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final HColor getColor() {
		return color;
	}

	public final void setColor(HColor color) {
		this.color = color;
	}

	public final void setDescription(String value) {
		this.description = value;
	}

	public final FontConfiguration getGroupDescriptionFontConfiguration() {
		final UFont font = UFont.serif(11);
		return new FontConfiguration(font, HColorUtils.BLACK, HColorUtils.BLACK, false);
	}

	protected final String getDescription() {
		return description;
	}

	public final Set<String> names() {
		return Collections.unmodifiableSet(names);
	}

	public boolean matches(LinkedElementNext tested) {
		// To be merged with containsNext
		return names().contains(tested.getElement().getName());
	}

	public boolean containsNext(NServer server) {
		return names.contains(server.getName());
	}

	public double getTopHeaderHeight(StringBounder stringBounder, ISkinParam skinParam) {
		final TextBlock block = buildHeaderName(skinParam);
		if (block == null) {
			return 0;
		}
		final Dimension2D blockDim = block.calculateDimension(stringBounder);
		return blockDim.getHeight();
	}

	public void drawGroup(UGraphic ug, MinMax size, ISkinParam skinParam) {
		final TextBlock block = buildHeaderName(skinParam);
		if (block != null) {
			final Dimension2D blockDim = block.calculateDimension(ug.getStringBounder());
			final double dy = size.getMinY() - blockDim.getHeight();
			size = size.addPoint(size.getMinX(), dy);
		}
		HColor color = getColor();
		if (color == null) {
			color = colors.getColorOrWhite(skinParam.getThemeStyle(), "#AAA");
		}
		size.draw(ug, color);

		if (block != null) {
			block.drawU(ug.apply(new UTranslate(size.getMinX() + 5, size.getMinY())));
		}
	}

	private TextBlock buildHeaderName(ISkinParam skinParam) {
		if (getDescription() == null) {
			return null;
		}
		return Display.getWithNewlines(getDescription()).create(getGroupDescriptionFontConfiguration(),
				HorizontalAlignment.LEFT, skinParam);
	}

}
