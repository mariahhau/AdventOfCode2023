using System;
using System.Collections.Generic;
using System.Linq;

namespace Day7Part2
{
    internal class Hand : IComparable<Hand>
    {
        string cards;
        int bid;
        Boolean joker = true;

        public Hand(string cards, int bid)
        {
            this.cards = cards;
            this.bid = bid;
           
        }

        public void toggleJoker()
        {
            joker = joker == true ? false : true;
        }

        public int GetBid() { return bid; }
        public string GetCards() { return cards; }
        public int CompareTo(Hand other)
        {
            
            if (other == null) return -1;

            string otherHand = other.GetCards();

            if (otherHand.Length < GetCards().Length) return -1;
            
            for (int i = 0; i < cards.Length; i++)
            {
                int card = getIntValue(cards[i]); // Hand.getIntValue(cards[i]);
                int otherCard = other.getIntValue(other.cards[i]);

                if (card < otherCard) return 1;
                else if (card > otherCard) return -1;


            }
            return 0;
        }
        //Returns the hand: 0 == five of a kind ... 6 = high card
        public int GetHand()
        {
            if (cards == null) return -1;

            List<int> jokers = new List<int>();
            Dictionary<char, int> labels = new Dictionary<char, int>();
            int i = 0;
            foreach (char card in cards)
            {
                if (labels.ContainsKey(card))
                {
                    labels[card]++;
                }
                else
                {
                    labels.Add(card, 1);
                }
                if (card == 'J')
                {
                    jokers.Add(i);
                }
                i++;

            }
            
            int hand = -1;
            int maxValue = labels.Values.Max();
            char[] highestKeys= labels.Where(x => x.Value == maxValue).Select(x => x.Key).ToArray();
            char highestKey =  highestKeys[0];
            
            if (joker && highestKeys.Contains('J'))
            {
                if (highestKeys.Length > 1)
                {
                    for (int j = 0; j < highestKeys.Length; j++) {
                        if (highestKeys[j] != 'J')
                        {
                            highestKey = highestKeys[j];
                            break;
                        }
                    }
                    
                } else
                {
                    foreach (char card in cards)
                    {
                        if (card != 'J')
                        {
                            highestKey = card;
                            break;
                        }
                    }
                }

            }
            
            if (joker && highestKey != 'J')
            {
                labels.Remove('J');
                foreach (int joker in jokers)
                {
                    labels[highestKey]++;
                }

            }
            
            switch (labels.Count)
            {

                case 1:
                    hand = 0; //five of a kind AAAAA
                    break;

                case 2:
                    {
                        int max = labels.Values.Max();

                        if (max > 3)
                        {
                            hand = 1; //four of a kind AA8AA
                        }
                        else
                        {
                            hand = 2; //full house AAA88 
                        }

                    }
                    break;
                case 3:
                    {
                        int max = labels.Values.Max();
                        if (max > 2)
                        {
                            hand = 3; //three of a kind AAA98
                            
                        }
                        else
                        {
                            hand = 4; //two pair A23A2
                            
                        }
                    }
                    break;
                case 4:
                    //one pair
                    hand = 5;
                    break;
                case 5:
                    //high card
                    hand = 6;
                    break;
                default:
                    break;
            }
            return hand;

        }


        public int getIntValue(char card)
        {
            if (Char.IsNumber(card))
            {
                return int.Parse(card.ToString());
            }
            switch (card)
            {
                case 'A':
                    return 14;
                case 'K':
                    return 13;
                case 'Q':
                    return 12;
                case 'T':
                    return 10;
                case 'J':
                    if (joker)
                    {
                        return 1;
                    }
                    else
                    {
                        return 11;
                    }
                default:
                    return 0;

            }

        }

    }
}
