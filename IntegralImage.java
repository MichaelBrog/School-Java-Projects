package A1Q2;

/**
 * Represents an integer integral image, which allows the user to query the mean
 * value of an arbitrary rectangular subimage in O(1) time. Uses O(n) memory,
 * where n is the number of pixels in the image.
 *
 * @author jameselder
 */
public class IntegralImage {

	private final int[][] integralImage;
	private final int imageHeight; // height of image (first index)
	private final int imageWidth; // width of image (second index)
	int[][] imageMultiplied; // new integral image datastructure

	/**
	 * Constructs an integral image from the given input image.
	 *
	 * @author jameselder
	 * @param image
	 *            The image represented
	 * @throws InvalidImageException
	 *             Thrown if input array is not rectangular
	 */

	public IntegralImage(int[][] image) throws InvalidImageException {
		integralImage = image;
		imageHeight = image.length;
		imageWidth = image[0].length;
		imageMultiplied = new int[imageHeight][imageWidth];
		// for rows
		for (int y = 0; y < imageHeight; y++) {
			// for columns
			if (image == null || integralImage[y].length != imageWidth) {
				throw new InvalidImageException();
			}
			for (int x = 0; x < imageWidth; x++) {
				// creating the top left corner of the new array (this might not be needed?
				if (x == 0 && y == 0) {
					imageMultiplied[y][x] = integralImage[y][x];
				}
				// creating the top row of new array
				if (y == 0 && x != 0) {
					imageMultiplied[y][x] = imageMultiplied[y][x - 1] + integralImage[y][x];
				}
				// creating the first column
				if (x == 0 && y != 0) {
					imageMultiplied[y][x] = imageMultiplied[y - 1][x] + integralImage[y][x];

				}
				// every other case uses the value above, and the value to he left, minus the
				// value in the top left corner
				// of the new array, in addition to the current value of the old array in order
				// to calculate the value.
				else if (y != 0 && x != 0) {
					imageMultiplied[y][x] = imageMultiplied[y - 1][x] + imageMultiplied[y][x - 1]
							- imageMultiplied[y - 1][x - 1] + integralImage[y][x];
				}
			}
		}
	}

	/**
	 * Returns the mean value of the rectangular sub-image specified by the top,
	 * bottom, left and right parameters. The sub-image should include pixels in
	 * rows top and bottom and columns left and right. For example, top = 1, bottom
	 * = 2, left = 1, right = 2 specifies a 2 x 2 sub-image starting at (top, left)
	 * coordinate (1, 1).
	 *
	 * @author jameselder
	 * @param top
	 *            top row of sub-image
	 * @param bottom
	 *            bottom row of sub-image
	 * @param left
	 *            left column of sub-image
	 * @param right
	 *            right column of sub-image
	 * @return
	 * @throws BoundaryViolationException
	 *             if image indices are out of range
	 * @throws NullSubImageException
	 *             if top > bottom or left > right
	 */
	public double meanSubImage(int top, int bottom, int left, int right)
			throws BoundaryViolationException, NullSubImageException {
		// case where it starts in the top left corner - Easy case
		int meanSubImageValue = 0;
		// errors will go here
		if (top > bottom || left > right) {
			throw new NullSubImageException();
		}
		if (top < 0 || bottom < 0 || top > imageHeight || bottom > imageHeight || left < 0 || right < 0
				|| left > imageWidth || right > imageWidth) {
			throw new BoundaryViolationException();
		}

		// case for top left corner
		if (top == 0 && left == 0) {
			meanSubImageValue = imageMultiplied[bottom][right];
		}
		// top = 0 case? Maybe
		if (top == 0 && left != 0) {
			meanSubImageValue = imageMultiplied[bottom][right] - imageMultiplied[bottom][0];
		}
		// if left = 0, maybe unneeded
		if (left == 0 && top != 0) {
			meanSubImageValue = imageMultiplied[bottom][right] - imageMultiplied[0][right];
		} else if (left > 0 && top > 0) {
			meanSubImageValue = (imageMultiplied[bottom][right] - imageMultiplied[bottom][left - 1]
					- imageMultiplied[top - 1][right] + imageMultiplied[top - 1][left - 1])
					/ ((bottom - top + 1) * (right - left + 1));
		}
		// Everything else

		return meanSubImageValue; // dummy value - remove once coded.
	}
}