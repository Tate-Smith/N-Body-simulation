# N-Body-simulation

## Introduction
This project is a 3D N-Body simulation, it takes in a JSON file as input and 
creates a CSV file as output, that contains the id, mass, position and velocity 
of each Planet, along with collision information. The input file can be in one
of two formats both starting with the simulation info. First format, the user specifies
every planet in the sim and their starting values (Mass, Position, Velocity), the other
format generates a random simulation given values of counts of Planets, Mass Range,
Position Range, Velocity Range

### Features
This N-Body simulation is in 3 Dimensions, and can hadle anywhere from 2-100000
Planets, It handles collisions by merging the two Planets, their mass, and conserving 
momentum. The program uses Leapfrog integration to keep accurate data. At > 500 planets 
the program uses an Octree to improve performance.

### Upcoming
- Performance optimizations
- Visualization
- More...

**File Format**

`{
  "simulation": {
    "step": 0.01,
    "steps": 10000,
    "G": 1.0,
    "softening": 0.1
  },
  "planets": [
    {
      "mass": 1000.0,
      "radius": 10.0,
      "position": [0, 0, 0],
      "velocity": [0, 0, 0]
    },
    {
      "mass": 1.0,
      "radius": 1.0,
      "position": [100, 0, 0],
      "velocity": [0, 4.7, 0]
    }
  ]
}`

**Or Generative Sim File Format**

`{
  "simulation": {
  "step": 0.01,
  "steps": 10000,
  "G": 1.0,
  "softening": 0.1,
  },
  "generation": {
    "count": 100,
    "massRange": [1, 100],
    "radiusRange": [1, 10],
    "positionRange": [-500, 500],
    "velocityRange": [-2, 2],
  }
}`

## How to run

In the Command Line:
`java "input_file_name"`

For Gen Sim:
`java "input_file_name" --gen`
