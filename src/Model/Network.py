import matplotlib.pyplot as plt
import matplotlib
import numpy as np
from numpy import random
from sklearn.datasets import fetch_mldata
from sklearn.metrics import mean_squared_error

class Network:

    def __init__(self, dimensions):
        """
        Initialise network with given dimensions

        Argument dimensions is tuple of size equal to the desired number of
        layers (including input and output layers).

        Each element of the tuple is the desired size of that specific layer.
        For exemple, dimensions = (10, 6, 2) would create a three-layer network
        with an input layer of 10 neurons, one hidden layer of 6 neurons and an
        output layer of 2 neurons.
        """
        self.dimensions = dimensions
        self.biases = [random.randn(i) for i in dimensions[1:]]
        self.weights = [random.randn(dimensions[i], dimensions[i-1])
                        for i in range(1, len(dimensions))]

    def __str__(self):
        return ("Network:" +
                "\n\tInput Size: " +
                str(self.dimensions[0]) +
                "\n\tOutput Size: " +
                str(self.dimensions[-1]) +
                "\n\tHidden Layers: " +
                str(len(self.weights) - 1))

    def feed_forward(self, a):
        """
        Return the ouput of the network givenan input vector a, a weigths
        matrix w and a biases vector b.
        """ 
        for w, b in zip(self.weights, self.biases):
            a = sigmoid(np.dot(w, a) + b)
        return a

    def output_cost(self, a, l):
        """
        Returns the result of the cost function given an output vector a
        and the associated label vector l in the form of the one-hot encoding
        of that label.
        The cost function itself is a simple mean squared error
        """
        return mean_squared_error(a, l)

    def train(self, train_set, epochs=1, batch_size=1, learning_rate=1):
        """
        Train the neural network using stochastic gradient descent.
        train_set takes the form of a list of tuples, each containing
        data and its associated label one-hot encoded.
        """
        for i in range(epoch):
            np.random.shuffle(train_set)
            batches = [train_set[j:j+batch_size]
                       for j in range(0, len(train_set), batch_size)]
            for batch in batches:
                self.feed_batch(batch, learning_rate)
        print("Epoch {} completed.".format(i))

    def feed_batch(self, batch, learning_rate):
        """
        Update the weights and biases according to the gradient descent using
        back propagation, given a batch and a learning rate.
        """

    def back_propagation(self, x, y):
        """
        Return a tuple (grad_w, grad_b) representing the gradient of the cost
        function for the weights and biases. grad_w and grad_b are lists of
        arrays similar to self.weights and self.bisases.
        """
        z_list = []
        a_list = []
        for w, b in zip(self.weights, self.biases):
            z = np.dot(w, a) + b
            z_list.append(z)
            a = sigmoid(z)
            a_list.append(a)
        


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


def main():
    """Fetching the MNIST dataset"""
    mnist = fetch_mldata('MNIST original')

##    """Print one digit for testing"""
##    print_digit(mnist['data'][42000])

    """The MNIST dataset contains 60'000 data points. The last 10'000 elements
    of the dataset are a prebuilt testing set."""
    train_set = list(zip(mnist.data[:60000], mnist.target[:60000]))
    test_set = list(zip(mnist.data[-10000:], mnist.target[-10000:]))

    network = Network((784, 397, 10))
##    Network.train(train_set)
    print(len(train_set))
    print(len(test_set))

    

if __name__ == "__main__":
    main()
    
