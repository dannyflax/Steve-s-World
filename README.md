## Steve's World [![Build Status](https://travis-ci.org/dannyflax/Steves-World.svg?branch=master)](https://travis-ci.org/dannyflax/Steve-s-World)

Start to a 3D Platform Demo written using [JOGL](http://jogamp.org/jogl/www/) and [JOGL Utils](https://github.com/dannyflax/JOGL-Utilities). You can control Steve, the 3D character, and rotate the camera.

You can also place custom-sized objects in the 3D world and test how the character intertacts with them.

It's written using a custom library on top of JOGL to load 3D model files and animations ([library source in seprate repo](https://github.com/dannyflax/JOGL-Utilities)) and it includes all the math for calculating motion, 2D => 3D projections, 3D collisions, and more.

## Build Instructions
Clone or download the project onto your local machine. If you don't already have [gradle](https://gradle.org), you can run the project the wrapper and the following commands:
```
./gradlew build
./gradlew run
```
Alternatively, if you do have gradle, the following two commands will run quicker:
```
gradle build
gradle run
```

## Controls

#### Basic Motion Controls:
 * 		Move Left - A
 * 		Move Right - D
 * 		Move Forward - W
 * 		Move Back - X
 * 		Move Diagonally - Q,E,Z,C
 * 		Jump - Space
 * 		Rotate Camera - R,T
 * 		Toggle god mode - G

#### God Mode Controls:
 * 		Switch view from axis - X,Y,Z (hold ctrl to flip)
 * 		Move Platform - Shift + Mouse Drag
 * 		Place Platform - P + Single Click
 * 		Resize Platform - X, Y, or Z and S (X,Y,Z refers to the axis on which you wish to resize)

##Screenshots
![Steve Start](https://github.com/dannyflax/Steve-s-World/blob/master/Screenshots/shot1.png)
![Steve Add Platform](https://github.com/dannyflax/Steve-s-World/blob/master/Screenshots/shot2.png)
![Steve on Flat Platforms](https://github.com/dannyflax/Steve-s-World/blob/master/Screenshots/shot3.png)
![Steve on Cubes](https://github.com/dannyflax/Steve-s-World/blob/master/Screenshots/shot4.png)
