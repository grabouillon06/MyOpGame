v0.4.0.0

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
- complete

purpose : 
- actor first move -> done
-- print path search metrics on screen -> done
-- print path to follow on screen -> done
-- move actor along the path -> done
- first avoidance -> delayed to next version -> ?
- first check of collision -> delayed to next version -> ?

remarks :
- application screen camera : (0,0) is bottom left, camera original position is in the middle of the application screen (256,256)
- mouse: (0,0) is top left
- actors are instantiated and are positioned in screen reference domain

future implementations :
- improve move computation to avoid blocking cases -> ?
- implements a separate path searcher independent that purpose would be to compute ALL path search of all actors of the game.
