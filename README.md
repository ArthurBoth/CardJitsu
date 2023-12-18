# CardJitsu
This is a personal project, to re-create the backend of the game "CardJitsu", a minigame from the children's game "Club Penguin"

## Specifications
This is a project made for fun and for testing my understanding on the subjects I'm currently studying in a practical sense.
All interactions are trough the terminal.
The player is gonna play against a bot in the code.
The bot will always make random choices.
One code run == One match. Once a match is over the code exits.

The specifications may change later, but this is what I am planning for now.

## Game rules
- CardJitsu is a 1v1 card game.
- Each player has it's own deck.
- At the start of every turn both players draw until they have five cards on their respective hands.
- Every turn, both players choose a card from their hand.
- The chosen cards "battle" and the winning card goes to their owner's score.
- In order to win, a player's score must have three cards of different colors and they all must be either of the same element or of different elements.
- When a card battles and does not win, it goes to the bottom of their owner's deck.

### Cards and Battles
Battles are decided by the cards attributes.
- Every card has a single element, *Fire*, *Water* or *Snow*.
- Every card has a number, these range from *[2-12]*.
- Every card as a color, *Red*, *Blue*, *Yellow*, *Green*, *Orange* or *Purple*.

A battle is determined by a card's element, where *Fire* beats *Snow*, *Water* beats *Fire* and *Snow* beats *Water*.
In case of both cards having the same element, then the card with the highest number wins.
In case of both cards having the same element and the same number, then it's a tie and no card wins.

- Cards with a number of 9 or above are called **Power cards** and have special abilities. They are:
    - **Power Reversal** *(Applies even if the card **looses**)*
        - Makes the tiebreaker of the next battle be the lowest number instead of the highest.
    - **Number modifier**  *(Applies even if the card **looses**)*
        - Has two variations, *+2* and *-2*.
        - The *+2* variant increases the number of the player's next card by 2.
        - The *-2* variant decreases the number of the opponent's next card by 2.
    - **Element removal** *(Applies only if the card **wins**)*
        - Has three variations *Fire*, *Water* and *Snow*.
        - Removes the oldest card with a specific element from the opponent's score.
        - Does not apply if the opponent's score does not have a card with that element.
    - **Color removal** *(Applies only if the card **wins**)*
        - Has six variations *Red*, *Blue*, *Yellow*, *Green*, *Orange* and *Purple*.
        - Removes the oldest card with a specific color from the opponent's score.
        - Does not apply if the opponent's score does not have a card with that color.
    - **Element change** *(Applies even if the card **looses**)*.
        - Has three variations *Fire->Snow*, *Water->Fire* and *Snow->Water*.
        - Turns both player's *X* element cards into *Y* element cards (they keep all other attributes), for the next battle only.
    - **Element block** *(Applies only if the card **wins**)*.
        - Has three variations *Fire*, *Water* and *Snow*.
        - Prevents the opponent from playing any card of a specific element, for the next battle only.
        - If all cards in a player's hand are blocked, they put their hand into the bottom of their deck, draw a new one and the **Element Block** effect expires.

# Updates
## Version 1
August 27th, 2023
This is the first version of the game, it includes all basic functionalities.
### Features
- Both the human player and the Bot player have different decks.
- The player can play a card, win and loose.
- When played, cards go to the correct place (either the score or the bottom of the deck)
- Both players' decks get shuffled frequently

### Known Issues
- Currently, decks are made with randomly generated cards, this can lead to awkward scenarios where the player only has cards of one element.
- Currently, when a player plays a card and wins a battle, the card goes both to the bottom of their deck and to their score
- Power Cards have not been implemented yet.

### Future Plans
These are things I intend on implementing in the future.
- Power Card Effects. These will likely come in waves. 
- Pre-made cards and decks. I plan on making *.csv* files with cards and decks, in order to make the game more balanced.
- Making the winner's score appear at the end so the player knows how/why that player won.
