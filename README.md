<<<<<<< HEAD
Java CPU Ray Tracer

A CPU-based ray tracer implemented from scratch in Java without using OpenGL/WebGL or external rendering engines.
This project renders 3D scenes consisting of spheres, materials, and lights using the Phong illumination model.

Features

- Perspective camera and primary ray generation
- Ray–sphere intersection
- Material system (ambient, diffuse, specular, shininess)
- Phong illumination shading
- Point and directional lights
- Shadow computation
- XML scene parsing (supports multiple spheres and lights)
- Image generation in PPM format
- Modular object-oriented architecture

Implemented Tasks (from assignment):

- T1 (10%): Black image generation (black_image.ppm)
- T2 (25%): Basic ray tracing of spheres (spheres.ppm)
- T3 (25%): Scene geometry and lighting parsed from XML
- T4 (30%): Phong illumination and shading (ambient, diffuse, specular)
- T5 (10%): Shadows rendered correctly in XML scenes (e.g., example3.xml)

Rendering Pipeline

1. Generate primary rays from camera
2. Intersect rays with scene geometry
3. Compute surface normals
4. Apply Phong illumination model
5. Cast shadow rays toward light sources
6. Write final color to image buffer (.ppm files)

Project Structure

java-ray-tracer/
 ├─ src/              # Java source files
 ├─ bin/              # Compiled .class files (generated)
 ├─ renders/          # Example rendered images (PPM format)
 │   ├─ black_image.ppm
 │   ├─ spheres.ppm
 │   ├─ shadow.ppm
 │   └─ scene.ppm
 └─ README.txt

Build & Run

Windows PowerShell:
- Compile all Java files into bin/
  javac -d bin src\*.java

- Run the main program
  java -cp bin Main

- Run with XML scene input
  java -cp bin Main scenes\example3.xml

Linux / macOS:
- Compile all Java files into bin/
  javac -d bin src/*.java

- Run the main program
  java -cp bin Main

- Run with XML scene input
  java -cp bin Main scenes/example3.xml

Output
All rendered images are written as .ppm files.
Images with _corrected.png suffix indicate adjusted camera settings for proper scene visualization.

Technical Notes

- Development Environment: Windows 11 (x64), OpenJDK 11.0.7
- Build Tool: javac
- Language Used: Java
- Geometry: Only spheres are supported
- Camera: Adjusted for proper zoom and full scene visualization

Purpose:
This project demonstrates a full CPU-based ray tracer with proper shading, lighting, shadowing, and XML scene parsing, highlighting object-oriented design and rendering fundamentals.

=======
# java-ray-tracer
CPU-based Java ray tracer with Phong shading, XML scene parsing, and PPM output.
>>>>>>> 9e8a159d4208066517e2db8b3f8148bbaa3c70a0
