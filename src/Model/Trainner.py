import numpy as np
import matplotlib.pyplot as plt
import matplotlib
from sklearn.datasets import fetch_mldata
from test import Network

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

def vectorize_label(j):
    e = np.zeros((10,1))
    e[int(j)] = 1.0
    return e


def main():
    """Fetching the MNIST dataset"""
    mnist = fetch_mldata('MNIST original')

##    """Print one digit for testing"""
##    print_digit(mnist['data'][42000])

    """The MNIST dataset contains 60'000 data points. The last 10'000 elements
    of the dataset are a prebuilt testing set."""
    train_set = list(zip(mnist.data[:60000], mnist.target[:60000]))
    test_set = list(zip(mnist.data[-10000:], mnist.target[-10000:]))
    train_set = [(train_set[i][0].reshape(-1,1), vectorize_label(train_set[i][1]))
                 for i in range(len(train_set))]
    test_set = [(test_set[i][0].reshape(-1,1), vectorize_label(test_set[i][1]))
                 for i in range(len(test_set))]

    network = Network([784, 30, 10])
    network.SGD(train_set, 5, 10, 3.0)
    print(network.evaluate(test_set))

if __name__ == "__main__":
    main()
