import numpy as np
from numpy import random

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
        self.biases = [random.randn(i, 1) for i in dimensions[1:]]
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
            a = self.sigmoid(np.dot(w, a) + b)
        return a

    def train(self, train_set, epochs, batch_size, learning_rate, test_set=None):
        """
        Train the neural network using stochastic gradient descent.
        train_set takes the form of a list of tuples, each containing
        data and its associated label.
        """
        for i in range(epochs):
            random.shuffle(train_set)
            batches = [train_set[j:j+batch_size]
                       for j in range(0, len(train_set), batch_size)]
            for batch in batches:
                self.feed_batch(batch, learning_rate)
            if test_set:
                print("Epoch {}: {} / {}".format(
                    i, self.test(test_set), len(test_set)))
            else:
                print("Epoch {} completed".format(i))  

    def feed_batch(self, batch, learning_rate):
        """
        Update the weights and biases according to the gradient descent using
        back propagation, given a batch and a learning rate.
        """
        grad_w = [np.zeros(w.shape) for w in self.weights]
        grad_b = [np.zeros(b.shape) for b in self.biases]
        for x, y in batch:
            delta_grad_w, delta_grad_b = self.backpropagation(x, y)
            grad_w = [new_w + delta_new_w
                      for new_w, delta_new_w in zip(grad_w, delta_grad_w)]
            grad_b = [new_b + delta_new_b
                      for new_b, delta_new_b in zip(grad_b, delta_grad_b)]
        self.weights = [w-(learning_rate/len(batch))*new_w
                        for w, new_w in zip(self.weights, grad_w)]
        self.biases = [b-(learning_rate/len(batch))*new_b
                       for b, new_b in zip(self.biases, grad_b)]
        
    def backpropagation(self, x, y):
        """
        Return a tuple (grad_w, grad_b) representing the gradient of the cost
        function for the weights and biases given an input vector x and a
        label y. grad_w and grad_b are lists of arrays similar to self.weights
        and self.bisases.
        """
        grad_w = [np.zeros(w.shape) for w in self.weights]
        grad_b = [np.zeros(b.shape) for b in self.biases] 
        # feed forward
        a = x
        a_list = [a]
        z_list = []
        for w, b in zip(self.weights, self.biases):
            z = np.dot(w, a) + b
            z_list.append(z)
            a = self.sigmoid(z)
            a_list.append(a)

        # compute the gradient for weights and biases
        delta = (self.cost_derivative(a_list[-1], y) *
                 self.sigmoid_derivative(z_list[-1]))
        grad_w[-1] = np.dot(delta, a_list[-2].transpose())
        grad_b[-1] = delta
        for L in range(2, len(self.dimensions)):
            z = z_list[-L]
            sp = self.sigmoid_derivative(z)
            delta = np.dot(self.weights[-L+1].transpose(), delta) * sp
            grad_w[-L] = np.dot(delta, a_list[-L-1].transpose())
            grad_b[-L] = delta
        return (grad_w, grad_b)

    def test(self, test_set):
        """
        Return the ratio of successful predictions over the test set's size.
        """
        predictions = [(np.argmax(self.feed_forward(x)), np.argmax(y))
                        for (x, y) in test_set]
        return sum(int(x == y) for (x, y) in predictions)

    def cost_derivative(self, a, l):
        """
        Derivative of the cost function.
        """
        return (a-l)

    def sigmoid(self, z):
        """
        Sigmoid functioin
        """
        return 1.0/(1.0+np.exp(-z))

    def sigmoid_derivative(self, z):
        """
        Derivative of the sigmoid function.
        """
        return self.sigmoid(z)*(1-self.sigmoid(z))

    def save(self, path):
        np.savez(path, self.weights, self.biases)

    def load(self, path):
        f = np.load(path)
        self.weights = f['arr_0']
        self.biases = f['arr_1']
