## Common Android Archiectural Patterns

**God Class / Object**
  It's an object that contains a high number of components. The components logic is coupled. Usually it's a very lengthy class, and we should avoid them at all costs. 

God, classes are very bad for our architecture in Android it is generally an Activity containing all the logic off our application.

Let's go ahead and talk about the architectural part turns we're going to review. 
  * The first one will be M.V.C, aka model view controller. 
  * The second one would be the M V P. Pattern, a k a model view presenter And
  * last but not least, we're going to talk about the MVV n model view view model


## MVC: Model View Controller

**Model**
- Represents the data models                        
- Manages the data’s states                         
- Includes the business logic of our application    
- Often used across different parts of our app.     

**View**
- Essentially it’s our layouts and views - The way we represent the data
- Renders the user interface

**Controller**
- Essentially it’s our activities and fragments
- Includes user’s interactions with our app
- The communication channel between our views and models


![image](https://user-images.githubusercontent.com/47377857/142866200-303f62e6-ab07-44fa-bde3-6c3a3f0c0fe1.png)


## MVP: Model View Presenter

**Model**
- Same as in the MVC pattern

**View**
- Our XML Layouts and views.
- Our activities and fragments:
  - In the Android world the two are strongly bonded with the views
  - Will implement an interface for the presenters actions

**Presenter**
- Has no relation to our views (Unlike MVC)
- Operations are invoked by our view (Activities or fragments)
- Views update is done via the view’s interface

![image](https://user-images.githubusercontent.com/47377857/142867812-a3911c10-59b5-45d0-b7e9-85385d6bb199.png)

## MVVM: Model View ViewModel

**Model and View**
- Same as in the MVP pattern

**ViewModel**
- Contains the Model
- Uses observable variables for update values
- On each value update, the relevant views will be updated

![image](https://user-images.githubusercontent.com/47377857/142868929-53bdf534-a12e-4d2f-a93f-2da2a195f6ea.png)


## Comparison

Primitive Types and Objects Size

![image](https://user-images.githubusercontent.com/47377857/142870018-22f8846c-c3a6-4e0d-bd68-eb829455bdd8.png)

## Why Promoting MVVM VS MVP: 

* ViewModel has Built in LifeCycleOwerness, on the other hand Presenter not, and you have to take this responsiblty in your side. 
* ViewModel doesn't have a reference for View, on the other hand Presenter still hold a reference for view, even if you made it as weakreference. 
* ViewModel survive configuration changes, while it is your own responsiblities to survive the configuration changes in case of Presenter. (Saving and restoring the UI state) 

## MVVM Best Pratice: 

 * Avoid references to Views in ViewModels. 
 * Instead of pushing data to the UI, let the UI observe changes to it. 
 * Distribute responsibilities, add a domain layer if needed. 
 * Add a data repository as the single-point entry to your data. 
 * Expose information about the state of your data using a wrapper or another LiveData. 
 * Consider edge cases, leaks and how long-running operations can affect the instances in your architecture. 
 * Don’t put logic in the ViewModel that is critical to saving clean state or related to data. Any call you make from a ViewModel can be the last one. 

