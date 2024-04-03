# Critical Ops matchmaking system

## Description

The Critical Ops matchmaking system will hold details about each player
and allows them to match against other players in certain game modes to gain a higher rating.
Matchmaking must be fair such that players of the same skill level and game mode are
matched against each other. Players who play the game *Critical Ops* 
can use this application to view their profile, play against others, 
and analyse their statistics from previous games. This project is important to
me because I grew up with this game (nearly 9 years ago), and it is 
currently the only game I still play while studying at UBC. The game
does not contain some of the game modes and player details in this project, so I thought this application 
could introduce  new features for the current game.

## User Stories

### Phase 0 & 1

- As a User, I want to be able to add a player to the server*
- As a User, I want to be able to view all the players in the server
- As a User, I want to be able to play against other players based on my rank and game mode
- As a User, I want to be able to receive rewards based on my current rank
- As a User, I want to be able to view the result of my previous games
- As a User, I want to be able to report other players for cheating, so they get banned
- As a User, I want to be able to view the current items I own in my inventory

### Phase 2

- As a User, I want to have the option to save the current server to file in the main menu
- As a User, I want to have the option to load a previous server from file in the main menu

(* although a single player can have multiple accounts, we will assume 
everyone in the server is a unique person as it would be impossible for 
someone to play against themselves. Essentially, every player in the server
represents a different user)

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by 
selecting the menu item labelled "Create Player."
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by
selecting the menu item labelled "View Players."
- You can locate my visual component by starting the application or viewing the menu button icons.
- You can save the state of my application by selecting the menu item labelled "Save Server."
- You can reload the state of my application by selecting the menu item labelled "Load Server."

extra: All the other buttons in the menu work and are successfully handled in the code. In other
words, my GUI fully represents the functionality of my application.


## Phase 4: Task 2

Tue Apr 02 16:37:15 PDT 2024
Player created.

Tue Apr 02 16:37:17 PDT 2024
Player created.

Tue Apr 02 16:37:19 PDT 2024
Players shown.

Tue Apr 02 16:37:21 PDT 2024
Player not created.

Tue Apr 02 16:37:23 PDT 2024
Players shown.

Tue Apr 02 16:37:25 PDT 2024
Player rank increased.

Tue Apr 02 16:37:25 PDT 2024
Added win to history.

Tue Apr 02 16:37:25 PDT 2024
Player rank did not change.

Tue Apr 02 16:37:25 PDT 2024
Added loss to history.

Tue Apr 02 16:37:25 PDT 2024
Matchmaking did occur (win).

Tue Apr 02 16:37:28 PDT 2024
Player rank increased.

Tue Apr 02 16:37:28 PDT 2024
Added win to history.

Tue Apr 02 16:37:28 PDT 2024
Player rank decreased.

Tue Apr 02 16:37:28 PDT 2024
Added loss to history.

Tue Apr 02 16:37:28 PDT 2024
Matchmaking did occur (win).

Tue Apr 02 16:37:32 PDT 2024
Added XD.45 Inked to inventory.

Tue Apr 02 16:37:35 PDT 2024
Added nothing to inventory.

Tue Apr 02 16:37:39 PDT 2024
Report count increased.

Tue Apr 02 16:37:41 PDT 2024
Report count increased.

## Phase 4: Task 3

One improvement I can see with my current UML diagram is that I 
could try to refactor the Console and GUI classes and extract their
common functionality into a parent class. For example, both classes
share the fields Server, JsonWriter, and JsonReader. I could make a parent
abstract class which could hold those fields and then make the Console
and GUI classes extend it. This way if I intend to add extra functionality
in the future which is common to both classes I could just add it in the abstract
class. Duplicated code is usually a bad thing so if I had more time I would
definitely do it. 

Also, if I intend to add methods with similar purposes
but different implementations, the compiler would remind me that I need
to implement the abstract methods in both classes. This is very helpful
as both user interface types have the same buttons/options. So another
refactoring process I could do is to create abstract methods in the 
parent class and implement them specifically in each class. This is a 
great way to get rid of duplication and not to miss out on what both
user interfaces must offer. 