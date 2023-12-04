use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn main() {
    // File hosts.txt must exist in the current path
    if let Ok(lines) = read_lines("./resources/day1_1.txt") {
        // Consumes the iterator, returns an (Optional) String
        let mut sum = 0;
        for line in lines {
            if let Ok(ip) = line {
                println!("{}", ip);
                if let Some(first) = ip.find(char::is_numeric) {
                    if let Some(last) = ip.rfind(char::is_numeric) {
                        println!("-- {:?} -- {:?}", first, last);
                        if let Some(f) = ip.chars().nth(first) {
                            if let Some(l) = ip.chars().nth(last) {
                                println!("-- {:?} -- {:?}", f, l);
                                let both = format!("{}{}", f, l);
                                let row: u64 = both.parse().unwrap();
                                sum += row;
                                println!("-- {:?} -- {:?}", sum, row);

                            }
                        }

                    }
                }

            }
        }
    } else {
        println!("--- can't read file")
    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
    where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}