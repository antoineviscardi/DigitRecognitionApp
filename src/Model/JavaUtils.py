"""
Offers utiliy methods to facilitate interfacing with Java.
"""
import numpy as np
import csv
import Network

def output_to_int(y):
    """Return an integer value given an output vector"""
    return np.argmax(y)

def feed_image(a):
    """Feed the image data to the network and return the output as an int"""
    net = Network.Network((784, 50, 10))
    net.load('net.npz')
    a = net.feed_forward(a)
    return output_to_int(a)

def read_image():
    """Read image pixels from file"""
    image_data = []
    path = '../../img.txt'
    with open(path, 'r') as f:
        reader = csv.reader(f)
        image_data = list(reader)
    return image_data

def get_net_output():
    """Use above functions to return the network output"""
    image_data = [float(i) for i in read_image()[0]]
    image_data = np.array(image_data).reshape((-1, 1))
    return feed_image(image_data)
    
