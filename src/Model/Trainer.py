import numpy as np
import matplotlib.pyplot as plt
import matplotlib
from sklearn.datasets import fetch_mldata
from Network import Network

def print_digit(digit):
    """Print one digit given a 784 digit input vector.
    This function is mainly used for testing."""
    plt.imshow(digit.reshape(28,28),
               cmap=matplotlib.cm.binary,
               interpolation='nearest')
    #plt.axis('off')
    plt.show()

def vectorize_label(j):
    """Return a one-hot encoded vector representing the given label"""
    e = np.zeros((10,1))
    e[int(j)] = 1.0
    return e

def fetch_wrap_data():
    """The MNIST dataset contains 60'000 data points. The last 10'000 elements
    of the dataset are a prebuilt testing set."""
    mnist = fetch_mldata('MNIST original')
    mnist = list(zip(mnist.data, mnist.target))
    mnist = [(mnist[i][0].reshape(-1,1)/255, vectorize_label(mnist[i][1]))
             for i in range(len(mnist))]
    train_set = mnist[:60000]
    test_set = mnist[-10000:]
    return (train_set, test_set)


def main():
    """Fetch and wrap the data, create a network, train it and save the best
    performing weights and biases"""
    train_set, test_set = fetch_wrap_data()

    network = Network([784, 100, 10])
    network.train(train_set, 30, 10, 3.0, test_set)
    
    network.save('net.npz')

if __name__ == "__main__":
    main()
