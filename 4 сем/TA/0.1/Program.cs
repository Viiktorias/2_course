using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _0._1
{
    class Program
    {
        static void Main(string[] args)
        {
            HashSet<long> set = new HashSet<long>();
            long sum = 0;
            string str;
            using (TextReader reader = new StreamReader("input.txt"))
            { 
                while ((str = reader.ReadLine()) != null)
                {
                    set.Add(long.Parse(str));
                }
            }
            foreach (long elem in set)
            {
                sum += elem;
            }
            using (StreamWriter streamWriter = new StreamWriter("output.txt"))
            {
                streamWriter.WriteLine(sum);
            }
        }
    }
}
