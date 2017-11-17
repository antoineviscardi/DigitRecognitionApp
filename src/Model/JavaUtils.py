"""
Offers utiliy methods to facilitate interfacing with Java.
"""
import numpy as np

# List where the image pixels are stored
image_data = []

def output_to_int(y):
    """Return an integer value given an output vector"""
    return np.argmax(y)

def add_pixel(f):
    """Add one pixel to image_data list given a float"""
    image_data.append(f)

def get_image_data_as_vector():
    """Return the image data list as a vector"""
    return np.reshape(image_data, (-1, 1))
