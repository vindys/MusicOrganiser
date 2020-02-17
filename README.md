# MusicOrganiser
MusicOrganiser is a sample project to which downloads data from api and displays in two dimension list.

MusicOrganiser is an android app which would pull songs details from https://api.myjson.com/bins/rov51.



Architecture followed is as follows:

  * Application is made with MVVM pattern.  
  * Viewmodel checks for new data from repository. 
  * Repository first checks in room db and then fetch from web for update. Latest copy from network is saved in room, hence it will provide offline support. 
  * We use NetworkBoundResource implementation to handle persistance better. 
  * Once we get response from the remoteDataSource we will save the data in database and will return as Livedata. 
  * Viewmodel handles the livedata using a mediatorlivedata which changes the data according to Filtertype. 
  * We are displaying the response using databinding in nested recyclerview which has one vertical recyclerview and horizontal recyclerview inside each. 
  * We are keeping horizontal recyclerview with a dynamic width for 60% of screen width and number of rows in horizontal list is controlled from spinner. 
  * Dependency is provided by Dagger2.
  
<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" width =400/>

  
