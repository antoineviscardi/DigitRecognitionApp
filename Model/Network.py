import matplotlib.pyplot as plt
import matplotlib
import numpy as np
from sklearn.datasets import fetch_mldata



def print_digit(digit):
    """
    Print one digit given a 784 digit input vector.
    This function is mainly used for testing.
    """
    
    plt.imshow(digit.reshape(28,28),
               cmap=matplotlib.cm.binary,
               interpolation='nearest')
    plt.axis('off')
    plt.show()

class Network:

    def __init__(self, dimensions):
        """
        Initialise network with given dimensions

        Argument dimensions is tuple of size equal to the desired number of
        layers (including input and output layers).
        Each element of the tuple is the desired size of that specific layer.
        """
        self.weights = []
        self.biases = []
        for i in range(1, len(dimensions)):
            self.weights.append(np.random.rand(dimensions[i], dimensions[i-1]))
            self.biases.append(np.random.rand())

    def __str__(self):
        return ("Network:" +
                "\n\tInput Size: " +
                str(self.weights[0].shape[1]) +
                "\n\tOutput Size: " +
                str(self.weights[-1].shape[0]) +
                "\n\tHidden Layers: " +
                str(len(self.weights)))

    def feed_forward(self, a):
        """
        Return the ouput of a network layer given
        an input vector a,
        a weigths matrix w
        and a biases vector b.
        """
        for w, b in zip(weights, biases):
             a = sigmoid(np.dot(w, a) + b)
        return a
    

def main():
    """Fetching the MNIST dataset"""
##    mnist = fetch_mldata('MNIST original')

##    """Print one digit for testing"""
##    print_digit(mnist['data'][42000])

    

if __name__ == "__main__":
    main()
    
