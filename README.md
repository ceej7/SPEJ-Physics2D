# SPEJ-Physics2D
A simple Physics2D Engine skeleton for java.
## Implementation
1. Basic Shape
    - Circle
    - Polygon
      - triangle and rectangle
      - others: clock-wise
2. Collision Detection
   1. SAT - Separating Axis Theorem
      1. Circle vs. Circle
      2. Circle vs. Polygon
      3. Polygon vs. Polygon
![img](img/SAT.jpg)
3. Impulse Resolution
   - ResolveCollision
   - PositionalCorrection
4. Interface
   - Trigger and Render
5. To begin
    ```JAVA
    //Step1. PhysicsEngineController
    PhysicsEngineController pc=PhysicsEngineController.getPhysicsEngineController();

    //Step2. Set Render
    GameRender gr=new GameRender();
    pc.setRender(gr);

    //Step3. Initial constant
    pc.initialGravity(10);
    pc.initialResistance(0.1,0.1);

    //Step4. Set Object
    pc.initialXXX(...);

    //Step5. Finalize
    pc.readyPhysicsRunning();
    pc.startPhysicsRunning();
    ```