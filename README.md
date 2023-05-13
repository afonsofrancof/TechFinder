# TechFinder
An android app that allows users to find, categorise, vote, comment and get directions to tech stores nearby.

It uses google's API to get the user location and the distance to the shops.

## Running
1. Run the [API](https://github.com/afonsofrancof/TechFinder-Database-API) on a server you own and fill the database with the stores, locations, etc.
2. Modify the file [DbAPI.kt](https://github.com/afonsofrancof/TechFinder/blob/main/app/src/main/java/com/example/techfinder/utils/DbAPI.kt) and change the IP and PORT to match your server's exposed API.
3. Compile with android studio and install the apk on your android phone.

## App screenshots

Ignore that most shops are 4000km away.
</br>
These screenshots are from an emulator without working location services.
</br>
It was defaulting the location to google's HQ.

### Main menu and login screen
![techfinder1](https://github.com/afonsofrancof/TechFinder/assets/56155712/0f21f602-a02e-4573-a6d4-515104c3b77d)

### Shop and vote screens
![techfinder 2](https://github.com/afonsofrancof/TechFinder/assets/56155712/88cf1ff7-d4cc-4068-89a5-7f882a4be374)

### Side Menu and favourites screen
![techfinder3](https://github.com/afonsofrancof/TechFinder/assets/56155712/e1bb04fa-62ad-42aa-95c1-ef5361dff1be)
