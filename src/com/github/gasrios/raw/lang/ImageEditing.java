/*
 * © 2016 Guilherme Rios All Rights Reserved
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.
 */

package com.github.gasrios.raw.lang;

import com.github.gasrios.raw.formats.ImageCIELCH;
import com.github.gasrios.raw.formats.ImageCIELUV;
import com.github.gasrios.raw.formats.ImageSRGB;

public class ImageEditing {

	public static ImageSRGB blackAndWhite(ImageSRGB image) {

		double[][][] im = image.getImage();

		for (int i = 0; i < im.length; i++) for (int j = 0; j < im[0].length; j ++) {
			double average = 0D;
			for (int k = 0; k < 3; k++) average += im[i][j][k];
			for (int k = 0; k < 3; k++) im[i][j][k] = average/3;
		};

		return image;

	}

	public static ImageCIELCH blackAndWhite(ImageCIELCH image) { return saturate(image, -1D); }

	public static ImageCIELCH saturate(ImageCIELCH image, double percentage) {

		double[][][] im = image.getImage();

		for (int i = 0; i < im.length; i++) for (int j = 0; j < im[0].length; j ++) im[i][j][1] *= (1 + percentage);

		return image;

	}

	// Only works for B&W images.
	public static ImageSRGB adjustLuminance(ImageSRGB image) {

		double[][][] im = image.getImage();
		double min = Double.MAX_VALUE, max = Double.MIN_VALUE;

		for (int i = 0; i < im.length; i++) for (int j = 0; j < im[0].length; j ++) {
			if (min > im[i][j][0]) min = im[i][j][0];
			if (max < im[i][j][0]) max = im[i][j][0];
		}

		for (int i = 0; i < im.length; i++) for (int j = 0; j < im[0].length; j ++) {
			double luminance = Math.normalize(im[i][j][0], min, max);
			for (int k = 0; k < 3; k ++) im[i][j][k] = luminance;
		}

		return image;

	}

	public static ImageCIELUV adjustLuminance(ImageCIELUV image) {

		double[][][] im = image.getImage();
		double min = Double.MAX_VALUE, max = Double.MIN_VALUE;

		for (int i = 0; i < im.length; i++) for (int j = 0; j < im[0].length; j ++) {
			if (min > im[i][j][0]) min = im[i][j][0];
			if (max < im[i][j][0]) max = im[i][j][0];
		}

		for (int i = 0; i < im.length; i++) for (int j = 0; j < im[0].length; j ++) im[i][j][0] = 100*Math.normalize(im[i][j][0], min, max);

		return image;

	}

}