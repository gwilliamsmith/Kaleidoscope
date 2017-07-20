# Kaleidoscope
This is a Graph-driven grid based cellular automaton, designed for research and geomertic image generation for the creation of coloring 
books (shameless plug for [Control Alt Color](https://www.amazon.com/Control-Alt-Color-Shayne-Alexander/dp/1545421331/ref=sr_1_1?ie=UTF8&qid=1500386753&sr=8-1&keywords=control+alt+color) here). Kaleidoscope has a number of features, some useful for changing how line generation works, some for geometric image generation, and some for both.

Kaleidoscope has three main growth modes, and one experimental: regular, mutation, and depth-based coloring are the main modes, while growth-based is the experimental mode.

## Table of Contents

1. [Important Threads](#important-threads)
2. [The Graph](#the-graph)
3. [The Queue](#the-queue)
4. [GraphNodes and GraphTuples](#graphnodes-and-graphtuples)
5. [Debug Menu](#debug-menu)
6. [Growth Modes](#growth-modes)
   * [Regular Growth Mode](#regular-growth-mode)
   * [Mutation Growth Mode](#mutation-growth-mode)
   * [Depth-Based Growth Mode](#depth-based-growth-mode)
   * [Growth Mode](#growth-mode)
7. [Contributing](#contributing)
   * [Creating Issues](#creating-issues)
8. [License](#license)


## Important Threads

While running, Kaleidoscope currently runs two important threads:
*  A [`Timer`](https://docs.oracle.com/javase/7/docs/api/java/util/Timer.html) used to repaint the `JPanel` where the `Graph` is displayed, on an interval of 1ms
*  Another thread (also a `Timer`), used to handle the growth logic for the `Graph`. This `Timer` has an interval set by the user, and can be controlled using either a slider at the bottom of the window, or by manutally entering the value by using the "Resize Grid" option in the Properties menu.
    * Note: While the timer can be set to an interval larger than 1 second (1000ms), the maximum value of the slider is 
    1000ms.
    
**Important Note:** While Kaleidoscope runs fairly well at lower numbers of nodes, higher numbers of nodes can slow down the software,  requiring more than 1ms to draw all of the nodes and connections. One of my ongoing efforts is to speed drawing up, so that larger `Graph`  sizes are more feasible to run.

## The [Graph](./src/graphvisualizer/Graph.java)

The Graph class is the most important class within Kaleidoscope, being the class that handles the logic running the cellular automaton. Currently, the main parts of the Graph are a matrix of [`GraphNode`](./src/graphvisualizer)s that make up the grid, and a selection of flags that control how the cellular automaton runs. Additionally, there is an ArrayList containing all nodes in the matrix (this will probably change soon, I'm not a huge fan of that structure anymore).

### [`GraphNode`](./src/graphvisualizer/GraphNode.java)s and [`GraphTuple`](./src.graphvisualizer/GraphTuple)s

The nodes that make up the grid and the lines that connect two nodes, `GraphNode`s and `GraphTuple`s are also highly important parts of what makes Kaleidoscope run. While more simple than the `Graph` class, the `GraphNode` and `GraphTuple` classes contain the logic that allows both to be displayed, and to connect to each other. Unlike many graph implementations, in Kaleidoscope, the nodes in the graph are what store connections between nodes. On a `GraphNode`, this is an ArrayList of `GraphTuple`s called `connections`. This structure is used so that it is easy to iterate through `GraphTuple`s connecting to the node in question. 

`GraphTuple`s serve two purposes: describing the connections between nodes, and generating genetic information to pass down to newly created lines. `GraphTuple`s can be asked to simply provide their current genetic information, or mutated genetic information, which will mutate the lifespan and the color of the child line, if those options are enabled. Additionally, there are `GraphTuple`s that can be designated as edges. Edge `GraphTuple`s do not have their lifespan reduced with each step of the automaton, and also do not reproduce.

Simple passing down of `GraphTuple` features is done with `GraphTuple.generateGTI()`, and passing down mutated features is done with `GraphTuple.generateMutatedGTI()`. Both functions return a `[GraphTupleInfo](./src/graphvisualizer/GraphTupleInfo.java)`, a class used to encapsulate `GraphTuple` features for use in reproduction.

**Genetic Information Held by GraphTuples**

 Field | Type | Eligable for mutation?
 :---: | :---: | :---:
 startHealth | int | Yes
 color | [Color](https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html)| Yes
 mutationPercentage | int | No - actual percentage is mutationPercentage/20000
 reprodcutionClock | int | No
 edge | boolean | No
 family | int | No
 depthColorIndex | int | No - incremented from line that created it
 
## The [Queue](./src/graphvisualizer/MyQueue.java)

Kaleidoscope uses an implementation of a quque that I've written, to scan the `Graph` for any lines that are eligible to reprouce. By iterating through the nodes in the `Graph`, and tracking all `GraphTuple`s that can reproduce, it guarantees that all lines that should reproduce on a given step do in fact reproduce. The queue is the first thing done when `Graph.takeStep()` is called, and the pre-construction of the queue is what allows patterns to grow symmetrically. 
 
## Growth Modes
 
### Regular Growth Mode
---

Regular growth mode is the default growth mode, and the one used for geometric pattern creation. In regular growth mode, lines reproduce and die, but do not mutate, and so never change color or lifespan. In this mode (and modes built off of it like [Mutation](#mutation-growth-mode) and [Depth-Based Coloring](#depth-based-coloring-mode)), lines follow simple rules for reprouction (images to illustrate this coming as soon as I have them):
 
 * Only reproduce if the line does not touch any lines in the direction it is to reproduce.
 * If there is already a line where one would be created, that line is left alone, and is not overwritten.
 * If the line is a straight line, create two diagonal lines at each end, each pair forming a right angle.
 * If the line is a diagonal line, create two straight lines at each end, each pair forming a right angle.
 * Lines lose one health(lifespan) per step, including the step in which they were created.
 * If a line has no health(lifespan) remaining, it dies and disappears.

### Mutation Growth Mode
---

Mutation growth mode is the mode used when you want to allow parent lines to pass on slightly different characterists to their children. Following the rules for [regular](#regular-growth-mode) growth mode, mutation growth mode has toggles for allowing/disallowing both lifespan and color mutation independently. As of right now, lifespan and color are the only two characteristics that have the ability to mutate, though more will likely gain the ability. Mutation percentage is input by the user, and currently is that user-input number/20000

### Depth-Based Coloring Mode
---

Depth-based coloring mode is the exact same mode as [regular](#regular-growth-mode) growth mode, except lines in this mode get their color from their generation. In this mode, colors cycle from red to green to blue, then back to red again, passing on colors that are a fixed interval away from their parent's colors. With multiple intervals available (all factors of 255), users can control how quickly lines rotate between colors.

For now, the depth-based color interval can be changed in the debug menu, though this is likely to change.

### Growth Mode
---

First off, I know this needs a new name. I'm open to suggestions.

Other than that, this is an experimental mode, utilizing the `food` property of `GraphNode`. In this mode, lines create one child, in the direction of the node with the highest `food` value. This mode is the least complete, and I'm not currently working in it. I will, however, explore any issues that might be opened regarding it.

## Debug Menu

I've implemented a debug menu into Kaleidoscope, containing some features that may be useful for advanced users. The debug menu can be accessed by holding down the control key, and pressing the 'D' key five times. The debug menu is likely to change - as I add new features and get feed back, new functionality may be added, or I may move functionality out of it into the menu.

## Contributing

If you want to contribute, feel free to fork and make your own changes, and I'll review them myself. I'd be happy to have help or hear any constructive advice anyone has to offer. Make sure to take a look at [CONTRIBUTING.md](./CONTRIBUTING.md) before submitting a pull request, but do keep in mind that those are guidelines, not hard-and-fast rules. I can (and will) accept anything I feel is really good , even if it hasn't followed the proper guidelines.

The issue tracker is [here](https://github.com/gwilliamsmith/GraphVisualizer/issues). If you run into a problem with Kaleidoscope and want it fixed, or want a good place to start, that's the place to look. Try not to open any duplicates issues, please.

Additionally, if there's any information that you think belongs on this readme, let me know! I'm probably not the best at writing these.

### Creating Issues

If you do feel the need to create an issue, please be descriptive and tag your issue with tags you find relevant. It'll help me (or anyone else) to work the issue to resolution if we have more information on it.

## License

Kaleidoscope currently has a (slightly modified) version of the MIT license. All I ask is that you don't sell Kaleidoscope or any project made from it. The license can be found [here](./license). If there's anything wrong with it, feel free to reach out to me at g.williamsmithiv@gmail.com, or by making an issue here. I'll get it resolved as fast as I am able.
