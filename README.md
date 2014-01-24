Caelum-Engine
=============

The Caelum Engine is a game engine/game utilities library, designed with a generic enough architecture so as to enable for near countless different games to be created using it.


Instructions
=============

In order to contribute to this repository, you will need:

1. An Eclipse instance.
2. The coding standards for this repo. (Optional, but recommended)
3. (Optional) The Android SDK.
4. Enough bandwidth to clone this repo (Duh)


Philosophy
============

The philosophy behind this game engine, besides being generic enough for different game genres, is:

* Simplicity: Besides our overly complicated launch system, creating your own game should be relatively simple; Currently, there are only 4 interfaces you need to implement: IGame, IRenderHUB, IScene, and IModelGroup.
* Straightforwardness: The Caelum Engine is designed with no frills, no unnecessary registries, and no "fancy" code; Just simple, direct, and effective features.
* Flexibility: If you don't want to use a feature, then you don't have to use it; The Caelum Engine uses null checks at several key points, enabling for features, such as rendering, to be completely bypassed. Also, the Caelum Engine is designed so that the only thing you have to wrap around is the interfaces needed to, well, interface with the engine.
* Portability: Since Java is a cross-platform programming language, it only makes sense that programs using it are portable, too. In fact, the Caelum Engine is so self-contained, you'll be able to play games you make on your Android phone, even if it wasn't designed for it.
* Usefulness: Several classes in the Caelum Engine are purely for convenience, such as TextHelper, EnumOS, and Buffer. That doesn't mean, however, that they're superfluous; On the contrary, many of the "convenience" classes in the Caelum Engine are to help consolidate code down to a simple function, constant, or object.
