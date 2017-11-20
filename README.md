# Digit Drawing Application
This is a small drawing application backed by a simple neural network trained with the MNIST dataset to recognize digits from 0 to 9.
The model was created in Pyhton without the use of any machine learning libraries. This is simply for personal learning purposes. 
The application itself was implemented in Java.

## The model
The Python module `Network` is where the class `Network` is implemented. It is a simple implementation of a stochastic gradient descent neural net. For obvious learning reasons, the implementation does not use any machine learning libraries and mostly relies on the Numpy package for its matrices operations. In fact, the network is nothing more than a set of wheights and biases, arranged as matrices. When you feed it an input vector, each layer's activation is computed by doing matrices operations on that vector using the wheights and biases. A really good explanation of the maths and how they translate to code can be found in the free book [Neural Networks and Deep Learning](http://neuralnetworksanddeeplearning.com/) by Micheal Nielsen. Besides, my code is highly inspired by the code presented in that book.

The images used in the MNIST dataset have 28 by 28 pixels wich translates to an input layer of 784 neurons. Also the output layer must be of 10 neurons since we are trying to recognizes digits from 0 to 10. I decided to go for a network with one hidden layer of 50 neurons. It gave me a result of 95.7% accuracy on the test set. Although this is a really good result, I know from [LeCun's, Bottou's, Bengio's and Haffner's 1998 paper](http://yann.lecun.com/exdb/publis/pdf/lecun-98.pdf) that more neurons and layers could produce even better results without any preprocessing. However, I was pretty satisfied with the result I got and adding more neurons and layers would be considerably more taxing on hardware, especially since my implementation does not use any GPU parallelization.

## The application
The application is a simple Swing based java application. I won't go into the details of how it is implemented since this is not the focus of this small project. Basically, it allows a user to draw an image on a canvas of 280 by 280 pixels. The size is important since it allows for easy shrinkage by a factor of 10 to produce an image of 28 by 28 pixels, the required size for the network.

Coupled with the network, the application performed poorly: it would recognized some digits bu only when well written in the center and even then it would make some big mistakes such as confusing a 1 for a 5. This is mainly due to a lack of preprocessing of the images before they are fed to the network. Indeed, the images from the MNIST dataset all have a similar look and feel. Thus, in order to show better results, my application should apply some filters to the drawn image to make it look as similar as possible to an MNIST image before feeding it to the Network.

## Possible Improvements
The biggest improvement that could be made is to add preprocessing capabilities in order to make the images drawn by the user as similiar as possible to the images from the MNIST dataset used to train the network.

From a software point of view, it would be ideal to have the network run on separate thread so that the application would not have to create and initialize a Network object before each evaluation. 


## Files Description
- Application
  - `Canvas.java`

    This is the class responsible for the canvas on which the user draws. It offers an interface to easily acces the image in a format       that can be fed to the network.

  - `DigitDrawingApp.java`
  
    Main class of the application. It creates the JFrame element, displays the text, buttons and canvas, fetch the image, feed it to the     network and displays the result.
  
- Model
  - `JavaUtils.py`
    
    Offers a script that can be run by `DigitDrawingApp` to feed an image to the network and get the result back.
    
  - `Network.py`
  
    The implementation of the `Network` class as described earlier.
  
  - `Trainer.py`
  
    Script that fetches the MNIST dataset, creates a `Network`, trains it and save its weights and biases.
