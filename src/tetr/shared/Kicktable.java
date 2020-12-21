package tetr.shared;

import java.awt.Point;

public class Kicktable {
    /*
    Order:
    0R,R0,R2,2R,2L,L2,L0,0L
    02,20,RL,LR
    */
    
    public static final Point[][][] kicktable_srsplus={  
        {//J, L, S, T, Z Tetromino Wall Kick Data
            
            {new Point( 0, 0), new Point(-1, 0), new Point(-1,+1), new Point( 0,-2), new Point(-1,-2)},
            {new Point( 0, 0), new Point(+1, 0), new Point(+1,-1), new Point( 0,+2), new Point(+1,+2)},
            
            {new Point( 0, 0), new Point(+1, 0), new Point(+1,-1), new Point( 0,+2), new Point(+1,+2)},
            {new Point( 0, 0), new Point(-1, 0), new Point(-1,+1), new Point( 0,-2), new Point(-1,-2)},
            
            {new Point( 0, 0), new Point(+1, 0), new Point(+1,+1), new Point( 0,-2), new Point(+1,-2)},
            {new Point( 0, 0), new Point(-1, 0), new Point(-1,-1), new Point( 0,+2), new Point(-1,+2)},
            
            {new Point( 0, 0), new Point(-1, 0), new Point(-1,-1), new Point( 0,+2), new Point(-1,+2)},
            {new Point( 0, 0), new Point(+1, 0), new Point(+1,+1), new Point( 0,-2), new Point(+1,-2)},
            
            {new Point( 0, 0),new Point( 0,+1),new Point(+1,+1),new Point(-1,+1),new Point(+1, 0),new Point(-1, 0)},
            {new Point( 0, 0),new Point(+1, 0),new Point(+1,+2),new Point(+1,+1),new Point( 0,+2),new Point( 0,+1)},
            
            {new Point( 0, 0),new Point( 0,-1),new Point(-1,-1),new Point(+1,-1),new Point(-1, 0),new Point(+1, 0)},
            {new Point( 0, 0),new Point(-1, 0),new Point(-1,+2),new Point(-1,+1),new Point( 0,+2),new Point( 0,+1)},
        },
        {//I Tetromino Wall Kick Data
            
            {new Point( 0, 0), new Point(+1, 0), new Point(-2, 0), new Point(-2,-1), new Point(+1,+2)},
            {new Point( 0, 0), new Point(-1, 0), new Point(+2, 0), new Point(-1,-2), new Point(+2,+1)},
            
            {new Point( 0, 0), new Point(-1, 0), new Point(+2, 0), new Point(-1,+2), new Point(+2,-1)},
            {new Point( 0, 0), new Point(-2, 0), new Point(+1, 0), new Point(-2,+1), new Point(+1,-2)},
            
            {new Point( 0, 0), new Point(+2, 0), new Point(-1, 0), new Point(+2,+1), new Point(-1,-2)},
            {new Point( 0, 0), new Point(+1, 0), new Point(-2, 0), new Point(+1,+2), new Point(-2,-1)},
            
            {new Point( 0, 0), new Point(+1, 0), new Point(-2, 0), new Point(+1,-2), new Point(-2,+1)},
            {new Point( 0, 0), new Point(-1, 0), new Point(+2, 0), new Point(+2,-1), new Point(-1,+2)},
            
            {new Point( 0, 0),new Point( 0,+1)},
            {new Point( 0, 0),new Point( 0,-1)},
            
            {new Point( 0, 0),new Point(+1, 0)},
            {new Point( 0, 0),new Point(-1, 0)},
        }
    };
}
