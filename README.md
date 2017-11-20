# Digit Drawing Application
This is a small drawing application backed by a simple neural network trained with the MNIST dataset to recognize digits from 0 to 9.
The model was created in Pyhton without the use of any machine learning libraries. This is simply for personal learning purposes. 
The application was implemented in Java.

## The model
The Python module 'Network' is where the class 'Network' is implemented. It is a simple implementation of a stochastic gradient descent neural net. For obvious learning reasons, the implementation does not use any machine learning libraries and mostly relies on the Numpy library for its matrices operations. In fact, the network is nothing more than a set of wheights and biases, arranged as matrices. When you feed it an input vector, each layer's activation is computed by doing matrices operations on that vector using the wheights and biases. A really good explanation of the maths and how they translate to code can be found in the free book Neural Networks and Deep Learning by Micheal Nielsen. Besides, my code is highly inspired by the code presented in that book.

The images used in the MNIST dataset have 28 by 28 pixels wich translates to an input layer of 784 neurons. Also the output layer must be of 10 neurons since we are trying to recognizes digits from 0 to 10. I first decided to go for a network with one hidden layer of 50 neurons. It gave me a result of 95.7% accuracy on the test set. Although this is a really good result, I know from LeCun's 1998 paper that  with more neurons and layers could produce even better results without any preprocessing. I therefore trained a network with 2 hidden layers of 500 and 150 hidden neurons respectively. It produced an accuracy of XX% which is a good improvement.

## The application
The application is a simple Swing based java application. I won't go into the details of how it is implemented since this is not the focus of this small project. Basically, it allows a user to draw an image on a canvas of 280 by 280 pixels. The size is important since it allows for easy shrinkage by a factor of 10 to produce an image of 28 by 28 pixels, the required size for the network.

Coupled with the network, the application performed really poorly: it would recognized some digits bu only when well written in the center and even then it would make some big mistakes such as confusing a 1 for a 5. This is manly due to a lack of preprocessing of the images before they are fed to the network. Indeed, the images from the MNIST dataset all have a similar look and feel. Thus, in order to show better results, my application should apply some filters to the drawn image to make it look as similar as possible to an MNIST image before feeding it to the Network.

## Conclusion

## References
