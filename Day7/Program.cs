using System;
using System.Collections.Generic;
using System.IO;

namespace Day7Part1
{
    internal class Program
    {   
        static readonly string filename = "..\\..\\input.txt";

        static void Main(string[] args){
                 
            string[] lines = File.ReadAllLines(filename);
            List<Hand>[] hands = new List<Hand>[7];
            
            for (int i = 0; i < hands.Length; i++)
            {
                hands[i] = new List<Hand>();
            }

            foreach (string line in lines)
            {
                string[] parts = line.Split(' ');
                Hand newHand = new Hand(parts[0], int.Parse(parts[1]));
                int index = newHand.GetHand();
                
                if (hands[index] == null)
                {
                  hands[index] = new List<Hand>();
                }
                hands[index].Add(newHand);
            
            }

            int rank = lines.Length;
            int total = 0;
            for (int i = 0; i < hands.Length; i++)
            {
                hands[i].Sort();
                foreach(Hand hand in hands[i])
                {
                    total += rank * hand.GetBid();
                    rank--;
                }
            }
            Console.WriteLine("Total winnings: " + total);

        }   

       
    }
}
