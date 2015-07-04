##Steve's World [![Build Status](https://travis-ci.org/dannyflax/Steve-s-World.svg?branch=master)](https://travis-ci.org/dannyflax/Steve-s-World)

Start to a 3D Platformer written using [JOGL](http://jogamp.org/jogl/www/) and [JOGL Utils](https://github.com/dannyflax/JOGL-Utilities). You can control Steve and rotate the camera. He can jump on platforms. If he falls into space, he'll drop back onto the main platform. You can also add platforms and create your own platform map.

##Build Instructions
Clone or download the project onto your local machine. If you don't already have it, install [gradle](https://gradle.org), then run the following two commands from the project's root directory:
```
gradle build
gradle run
```

##Controls

####Basic Motion Controls:
 * 		Move Left - A
 * 		Move Right - D
 * 		Move Forward - W
 * 		Move Back - X
 * 		Move Diagonally - Q,E,Z,C
 * 		Jump - Space
 * 		Rotate Camera - R,T
 * 		Toggle god mode - G

####God Mode Controls (profusely complicated):
 * 		Switch view from axis - X,Y,Z (hold ctrl to flip)
 * 		Move Platform - Shift + Mouse Drag
 * 		Place Platform - P + Single Click
 * 		Resize Platform - X, Y, or Z and S (X,Y,Z refers to the axis on which you wish to resize)

##Screenshots
![Steve Start](https://github.com/dannyflax/Steve-s-World/blob/master/Screenshots/shot1.png)
![Steve Add Platform](https://github.com/dannyflax/Steve-s-World/blob/master/Screenshots/shot2.png)
![Steve on Flat Platforms](https://github.com/dannyflax/Steve-s-World/blob/master/Screenshots/shot3.png)
![Steve on Cubes](https://github.com/dannyflax/Steve-s-World/blob/master/Screenshots/shot4.png)
