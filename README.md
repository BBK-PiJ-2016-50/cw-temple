# cw-temple

## Overview
This program allows an explorer to navigate a cavern to find an orb.  Once the orb is located, the explorer must escape before the cavern collapses.
There are two phases to this - the explore phase and the escape phase.

### Explore Phase
The explorer must find their way to the orb, however the layout of the cavern is unknown, except for information regarding the tile the explorer is standing on and its neighbouring tiles.
The route must ideally be done in as few steps as possible as this will help to get a better score at the end.
The implemented solution builds up a map of nodes.  At each point the node is added to a stack.  The neighbours of the node are then evaluated and a closest neighbour to the orb is picked. This neighbour is then moved to and the process begins again.
If a dead end is reached, the nodes are popped from the stack until a node with an unvisited neighbour is reached.  This neighbour is then moved to and the process repeats.

### Escape Phase
Once the orb has been found the escape phase begins.  The cavern completely changes, however the explorer is provided with the exact layout of the cavern.  Tiles may also contain gold.
The explorer must collect as much gold as possible and find the exit before the time runs out.  A further issue to deal with is that the time does not decrease sequentially and is dependent on the weighted values of the edges that connect the nodes of the cavern.
The implemented solution uses Dijkstra's algorithm to find shortest routes between nodes.
At each point an optimal route from the current position to the nearest gold tile is generated.  In the same step the optimal route from that gold tile to the exit is calculated.  
The nodes that form this route each have edges of different values.  These are added together and compared to the time remaining.
If this value is less than the time remaining, the explorer can go and collect the gold from the tile, and the route to the tile is added to a bestRoute list.
At each step this process is repeated, and the bestRoute list is gradually built up.
At the point that there is not enough time to collect gold then the explorer immediately goes to the exit.  If the route to the exit contains gold, then this is picked up as well.

At the end of the explore phase the bonus multiplier and the gold are multiplied together to give a score.

## Limitations
The solution has been run through 1000's of different seeds and appears to successfully locate the orb, and retreat to the exit within time, eveytime.
However, in terms of gaining as high a score as possible, there are a couple of limitations with the solution:

* In the explore phase, when checking for the next closest node to the orb, if there are two or more nodes that are the same distance away, no further checks are made on which one of these would be best to take.
The solution selects one of them and goes with it.  If it turns out to be the wrong option, there is no corrective action, and in some cases this can lead the explorer wasting their time by exploring a large and irrelevant section of the cavern before back-tracking their way back to the place where the original route decision was made.
This can, in some cases, lead the bonus multiplier to drop to 1.
A potential solution may involve first checking to see if there is more than one node with equal distances to the orb.  If there is then choose one and move to it.
If a neighbour of this node is now closer to the orb, then keep going with this route.  However if it has taken the explorer further away, then the explorer needs to backtrack and take one of the other closest node options from the previous step.

* In the escape phase, the explorer doesn't favour tiles with higher amounts of gold over those with lesser amounts of gold.  The solution only looks at which is the next closest tile with gold of any value.
This means that if there is a large concentration of high value tiles in one area of the cavern, but they are far away, the explorer may end up only picking up low amounts of gold from other tiles.
The result of this being that the explorer potentially misses out on collecting the maximum amount of gold in the time available.
A potential solution could be to somehow divide the map up into sections and look at the average gold value of each section.  Then look at which section would be the most profitable to go to with remaining time in mind.  
Then at this point the solution that was actually implemented could be used.

The reason these limitations were not addressed is because a fair amount of time would be required to implement solutions and I think this time would be better spent studying for exams.

## Classes
The following classes were created/modified in order to support the solution.
These reside in the 'src\main\java\student' package:

* Explorer.java
* ExploreGraph.java
* ExploreGraphImpl.java
* GraphNode.java
* GraphNodeImpl.java 
* EscapeRoute.java
* EscapeRouteImpl.java
* EscapeRouteUtils.java

The other additional classes were created to support unit testing.
These reside in the 'src\test\java\test' package:
* ExploreGraphTest.java
* GraphNodeTest.java
* EscapeRouteTest.java
