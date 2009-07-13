# messagepub-java

A library to interact with the messagepub REST API using Java. Visit [messagepub.com](http://messagepub.com) for more information.

## Installation (using git)

    git clone git://github.com/dambalah/messagepub-java.git 

## Installation (using tarball)

Download the latest package from [messagepub.com/packages/messagepub-java.tar.gz](http://messagepub.com/packages/messagepub-java.tar.gz)

Unzip the package:

    tar xzvf messagepub.tar.gz    


Note: If you've downloaded the source, you can create the package using make:

    make package

## How to run the tests that come with the package

Once you've obtained the source code, take a look at the _src/MessagePubTest.java_ code to see how you can use the library.

To run the tests, search for the string _TODO_ within the _src/MessagePubTest.java_ file and update the file as explained in the comments.

Once you've updated the test file, simply run:

    make test
    
Take a look at the output and check your account at [messagepub.com](http://messagepub.com) to see your messages being sent.


## Documentation

To understand the messagepub API, read [our documentation](http://messagepub.com/documentation)

For information on the messagepub-java library, look at the files in the _docs/_ folder.

To generate the documentation, use make:

    make docs

    
    





