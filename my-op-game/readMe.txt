v0.5.0.0

tile: 32 x 32 pixels
world: 256 x 128 meters -> 256 x 128 tiles -> 8192 x 4096 pixels
application screen: 32 x 16 meters -> 32 x 16 tiles -> 1024 x 512 pixels 
actor0_0 at (255,127) meters  
actor0_1 at (10,15) meters
actor0_2 at (54,76) meters

control panel height: 128 pixels

minimap scaling factor: 1/32
minimap size: 256 x 128 pixels
minimap world: 256 x 128 pixels
minimap application screen: 32 x 16 pixels 
minimap actor0_0: 2 x 2 pixel
minimap actor0_1: 2 x 2 pixel
minimap actor0_2: 2 x 2 pixel

status : 
- on going

purpose : 
- create MyStage0 class and reorganize code around it -> done
- insert reset mechanism -> done
- A* algorithm
-- replace multiple F lists by a single F list -> not valid anymore as multiple lists simplifies the sort operation
-- revisit A* algorithm -> done -> sorted open list according to F values 
-- renamed APathElement in AStarElement -> done
-- added AStar class -> done
- D* Lite algorithm
-- imported D* Lite algorithm from daniel beard
-- MyActor0 now follows the D* lite path
- map visualization
-- layer 2 and object layer are not visible
-- only info for discovered blocks are printed on the screen -> done

remarks :
- The D* Lite is now imported in the project. In this version both A* path search and D* Lite path search exist and compute paths, 
  nevertheless the MyActor0 follows the D* Lite path only. Reset is OK.

future implementations :

