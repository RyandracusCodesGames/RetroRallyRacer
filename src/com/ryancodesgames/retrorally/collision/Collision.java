
package com.ryancodesgames.retrorally.collision;

public class Collision
{
    /**
     * Collision algorithm for terrains. Input all vertices of the terrain to check it
     * against an object.
     * @param x X-coordinate of the object.
     * @param z Z-coordinate of the object.
     * @param vertex Array of all vertices of the terrain is the format [x,y,z,x,y,z,...,x,y,z].
     * @param sizeof_vertex Size of the array containing of the terrain vertices.
     * @return New Y-coordinate of the object.
     */
    public static float getHeight3D(float x, float z, float[] vertex, int sizeof_vertex)
    {
	int vec_a = 0, vec_b = 0, vec_c = 0;
	for(int i = 0; i < sizeof_vertex; i+=9)
        {
		float d1 = (x - vertex[i+3]) * (vertex[i+2] - vertex[i+5]) - (vertex[i+0] - vertex[i+3]) * (z - vertex[i+5]);
		float d2 = (x - vertex[i+6]) * (vertex[i+5] - vertex[i+8]) - (vertex[i+3] - vertex[i+6]) * (z - vertex[i+8]);
		float d3 = (x - vertex[i+0]) * (vertex[i+8] - vertex[i+2]) - (vertex[i+6] - vertex[i+0]) * (z - vertex[i+2]);
                
		boolean negative = (d1 < 0) || (d2 < 0) || (d3 < 0);
		boolean positive = (d1 > 0) || (d2 > 0) || (d3 > 0);
		boolean collided = !(negative && positive);
                
		if(collided)
                {
                    vec_a = i;
                    vec_b = i+3;
                    vec_c = i+6;
                    break;
		}
	}
        
	float a = (vertex[vec_b+1] - vertex[vec_a+1]) * (vertex[vec_c+2] - vertex[vec_a+2]) - (vertex[vec_c+1] - vertex[vec_a+1]) * (vertex[vec_b+2] - vertex[vec_a+2]);
	float b = (vertex[vec_b+2] - vertex[vec_a+2]) * (vertex[vec_c+0] - vertex[vec_a+0]) - (vertex[vec_c+2] - vertex[vec_a+2]) * (vertex[vec_b+0] - vertex[vec_a+0]);
	float c = (vertex[vec_b+0] - vertex[vec_a+0]) * (vertex[vec_c+1] - vertex[vec_a+1]) - (vertex[vec_c+0] - vertex[vec_a+0]) * (vertex[vec_b+1] - vertex[vec_a+1]);
	float d = (a * vertex[vec_a] + b * vertex[vec_a+1] + c * vertex[vec_a+2]);
        
	return (d - a * x - c * z) / b;
    }
}
