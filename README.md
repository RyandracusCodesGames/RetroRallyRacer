# RetroRallyRacer
An open-source 3D game engine designed to facilitate fully textured polygonal graphics for a racing tech demo that is built from the ground up and software rendered.
## About
Retro Rally Racer is the name of this tech demo to demonstrate the full technical skills that I currently possess in three-dimensional graphics programming. The game aims to simulate a popular subgenre of racing games from the 5th generation console era with a low screen resolution, low-resolution textures, low polygon game objects, sprites to simulate character models, and a cheesy soundtrack to help bring back the wonderfully awful game music of that era.
## Details of the engine
I have dubbed this engine, the Retro Rally 3D Game Library which was heavily inspired by my reading the public documents provided by Sega to introduce their SGL, Sega 3D Game Library, which covered all the topics necessary to produce 3D graphics on the Sega Saturn. In that same vein, I learned a lot about the proper architecture of a graphics engine and the full range of functions necessary to begin my own development process.

As I mentioned previously, the purpose of this demo is to illustrate the full range of my computer graphics repertoire, so this demo uses no external graphics APIs or graphics libraries not provided by the native Java graphics library which even then I use very sparingly with replacements of my own rasterization algorithms when possible. Here is a full list of the architecture and specifications of the engine:
            
* The engine is a polygon rasterizer, that uses geometric primitives such as lines to draw 3D shapes on a 2D surface.
* Fully software rendered, with no explicit use of hardware acceleration.
* Utilizes a frame buffer provided by Java to fill in pixel information to the display.
* Features three drawing modes, wire-framed, surfaced, and textured polygons.
* Custom rasterization algorithms for drawing lines, triangles, and sprites to the screen.
* Perspective Correct texturing.
* Dynamic lighting is influenced by the algorithms found in OpenGL.
* Static light maps based on Quake for static lighting throughout certain scenes.
* Utilizes back-face culling, a depth buffer, and the painter's algorithm to solve the visible surface determination problem.
* Utilizes an attribute class to determine how each triangle should be processed throughout the graphics pipeline.
* Custom math library to process vector and matrix operations necessary for 3D games.
* Custom color class to avoid using the very slow Color class provided by Java.
* Unfinished collision system
* Map editor and loader to process maps in an efficient manner in the game.
* Currently supports up to 19 different game objects to build your own maps.
## Demo Project Examples
![img1](https://user-images.githubusercontent.com/108719757/218852164-c9c1e1eb-289c-4be9-bd86-b95a5e9b1736.png)
