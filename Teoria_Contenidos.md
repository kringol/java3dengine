## 1) Introducción ##

### 1.a) Desarrollo y Estado actual de la Industria de Video Games ###
  * Mercado
  * Presupuesto de un proyecto de Video Game (_3D Game Engine Programming - page 21_)

### 1.b) Engines 3D - Intro ###
  * Qué es un Engine 3D?
  * Aplicaciones
  * Ejemplos de Engines (Comerciales y Open Source)

## 2) Conceptos básicos de 3D Game Programming ##

### 2.a) Introducción ###
  * Concepto de 3D Model
  * Qué es Rendering?
  * Sistemas de Coordenadas
  * 3D Shapes (vertices, polygons, mesh, faces, backface...)

### 2.b) Introducción al Display de un modelo ###

  * Transformaciones, Rendering, Raster
  * View Frustum, Camaras
  * Luces, shading
  * Rendering pipeline
  * Qué son los GPUs?
  * Texture mapping
  * Skyboxes
  * ~~Scenegraph y Java3D Scenegraph~~ (**irá en el capítulo 4**)

### 2.c) Librerías gráficas ###
  * OpenGL, DirectX
  * Java
    * JNI -> jogl, lwgjl

=== 2.d) Intro a Renderización de Escenarios === (**no pondremos esta sección**)
  * Scene Management
    * Culling (occlusion culling, backface culling, view frustum culling)
    * LOD
    * Quadtrees
    * BSP
    * Portals

## 3) Qué es un 3D Game Engine? ##

  * Funcionalidad que debe brindar un 3D Game Engine
  * Arquitectura básica de un 3D Game Engine

## 4) Nuestro 3D Game Engine ##
### 4.a) Alcance ###
  * Objetivos y Presunciones (Extensibilidad, Ease of use, Portabilidad, ...)
  * Multithreading aplicado a Engines 3D
  * Modelo flexible para configurar los threads de procesamiento
  * Beneficios de Java en la programación 3D
  * Elementos no considerados (Performance, Audio, Physics, AI, Networking, ...)


### 4.b) Arquitectura e Implementación ###
  * Scenegraph, Java3D -> Scenegraph tutorial de Xith3D
  * Thirdparty libs
    * Xith3D
    * JInput (HIAL ? )
  * Arquitectura
    * Modulos y dependencias
    * Diseño de clases, explicación de cada módulo
    * Design Patterns aplicados
    * Diagramas de secuencia básicos
  * Implementación de Behaviors
  * Testing con JUnit

### 4.c) Ciclo de vida de creación de un 'World' ###
  * Configuración y ejecución
  * Demo

## 5) Conclusión ##
  * Lecciones aprendidas
  * Conclusiones
  * Pendientes


# --------------------------------- #

## Fuentes ##
  * Gonzales Clua, Bittencourt, _Desenvolvimiento de Jogos 3D_,PUC Rio
  * Finney, _3D Game Programming (All in One)_, Thomson (Chapters 3, 12, 18)
  * Zerbst, _3D Game Engine Programming_, Thomson
  * Eberly, _3D Game Engine Architecture_, Morgan Kauffman
  * Davison, _Killer Game Programming in Java_, O'Reilly
  * David Brackeen, Bret Barker, Laurence Vanhelsuwé, _Developing Games in Java_, New Riders
  * Dunn, Parberry, _3D Math Primer_, Wordware (pipeline gráfico)
  * Realtime Rendering in DirectX (iluminación)
  * Xith3D in a Nutshell
  * Java 3D Tutorial
  * Free And Open Source Licensing (White Paper), Sun Microsystems Inc.
  * Wikipedia
  * Otros... (links, etc...)