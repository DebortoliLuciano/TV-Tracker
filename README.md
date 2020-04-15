# TV-Tracker

## Description
TV-Tracker is an android app designed to allow a user to search for shows and add them to a watch list or a watched list. This app also provides info on said shows such as the time, day and what station it is on. It also includes info as to whether the show is still running.

## Getting Started

### Home
On startup of the app you will be presented with the **Home** screen. This screen will be initialy mostly blank. Typing the name of a show into the search bar and clicking the magnifiying glass next to it will populate the screen with shows that match the search. The returned shows will at least contain the name you entered into the search bar. Tapping on one of the stars will prompt the app to ask you if you want to add that show to the watch list. If you long click on a show that is in the watched list or watch list it will prompt you to remove it from the list. Clicking on one of the items will take you to the **Details** screen.

![Home Screen](https://github.com/DebortoliLuciano/TV-Tracker/blob/master/home.PNG "Home Screen")

### Details
When you tap on a show on one of the screens you will be taken to this screen. It displays an image, title, info on when and where to watch the show (may be absent if that info was not provided by the API), a description and two buttons to add and remove from the watch list and watched list. If the show is on one of the lists the button will remove it from the list rather than add it.

![Details Screen](https://github.com/DebortoliLuciano/TV-Tracker/blob/master/details.PNG "Details Screen")

### Watch List
When you click on the **Watch List** button in the side nav it will take you to this page. This page will display any shows that have been added to the watch list. Tapping on the show will take you to the **Details** page where you can add it to the watched list. Long clicking or tapping on the star will prompt it to ask you if you want to remove it from the list.

![Watch Screen](https://github.com/DebortoliLuciano/TV-Tracker/blob/master/watch.PNG "Watch Screen")

### Watched List
When you click on the **Watched List** button in the side nav it will take you to this page. This page will display any shows that have been added to the watched list. Tapping on the show will take you to the **Details** page where you can add it to the watch list. Long clicking or tapping on the star will prompt it to ask you if you want to remove it from the list.

![Watched Screen](https://github.com/DebortoliLuciano/TV-Tracker/blob/master/Watched.PNG "Watched Screen")

### Contact
When you click on the **Contact** button in the side nav it will take you to this page. This page has multiple buttons that allow you to get in contact with us for assistance.

![Contact Screen](https://github.com/DebortoliLuciano/TV-Tracker/blob/master/contact.PNG "Contact Screen")

### Help
When you click on the **Help** button in the side nav it will take you to this page. This page is a view pager that will walk you through how to use the application.

![Help Screen](https://github.com/DebortoliLuciano/TV-Tracker/blob/master/help.PNG "Help Screen")

### Database
![Database](https://github.com/DebortoliLuciano/TV-Tracker/blob/master/database.png "Database Schema")

## Compatibility
Android
Minimum API Level 19
Google Play Store
Tested on Pixel 2

## Authors

| NAME | ACCOUNT | EMAIL |
| ---- | ------- | ----- |
| Luciano DeBortoli | https://github.com/DebortoliLuciano | luciano.debortoli31@stclairconnect.ca |
| Saad Amjad | https://github.com/SaadMAmjad | TBA |

## Liscense

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
