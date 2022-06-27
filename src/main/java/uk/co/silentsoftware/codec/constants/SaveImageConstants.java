/* Gameboy Camera Lib
 * Copyright (C) 2022 Silent Software (Benjamin Brown)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.silentsoftware.codec.constants;


public class SaveImageConstants {

    public static final int IMAGE_WIDTH = 128;

    public static final int IMAGE_HEIGHT = 112;
    public static final int SMALL_IMAGE_WIDTH = 32;
    public static final int SMALL_IMAGE_HEIGHT = 32;

    public static final int IMAGE_START_LOCATION = 0x2000;
    public static final int NEXT_IMAGE_START_OFFSET = 0x1000;

    public static final int SMALL_IMAGE_START_OFFSET = 0xE00;

    public static final int SMALL_IMAGE_LENGTH = 0x100;

    public static final int IMAGE_LENGTH = 0xE00;
    public static final int MAX_SUPPORTED_IMAGES = 30;

    private SaveImageConstants(){}
}
