use std::fs::{File, OpenOptions};
use std::io::{self, BufRead};
use std::path::Path;

fn main() {
    // File hosts.txt must exist in the current path
    if let Ok(lines) = read_lines("./resources/day1_1.txt") {
        // Consumes the iterator, returns an (Optional) String
        let mut sum = 0;
        for a_line in lines {
            if let Ok(line) = a_line {
                let first = get_first_number(&line);
                println!("{}", line);

                if let Some(first) = get_first_number(&line) {
                    if let Some(last) = get_last_number(&line) {
                        println!("-- {:?} -- {:?}", first, last);
                        println!("-- {:?} -- {:?}", first, last);
                        let both = format!("{}{}", first, last);
                        let row: u64 = both.parse().unwrap();
                        sum += row;
                        println!("-- {:?} -- {:?}", sum, row);
                    }
                }
            }
        }
    } else {
        println!("--- can't read file")
    }
}

fn get_first_number(line: &String) -> Option<char> {
    let mut position = line.len() + 1;
    let mut value: Option<char> = None;
    if let Some(pos) = line.find(char::is_numeric) {
        if let Some(number) = line.chars().nth(pos) {
            position = pos;
            value = Some(number);
        }
    }
    for pattern in ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"] {
        if let Some(pos) = line.find(pattern) {
            println!(" << {} << {} << {}", pattern, pos, position);
            if pos < position {
                position = pos;
                value = match pattern {
                    "one" => Some('1'),
                    "two" => Some('2'),
                    "three" => Some('3'),
                    "four" => Some('4'),
                    "five" => Some('5'),
                    "six" => Some('6'),
                    "seven" => Some('7'),
                    "eight" => Some('8'),
                    "nine" => Some('9'),
                    &_ => None }
            }
        }
    }
    value
}
fn get_last_number(line: &String) -> Option<char> {
    let mut position = 0;
    let mut value: Option<char> = None;
    if let Some(pos) = line.rfind(char::is_numeric) {
        if let Some(number) = line.chars().nth(pos) {
            position = pos;
            value = Some(number);
        }
    }
    for pattern in ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"] {
        if let Some(pos) = line.rfind(pattern) {
            if pos > position {
                position = pos;
                value = match pattern {
                    "one" => Some('1'),
                    "two" => Some('2'),
                    "three" => Some('3'),
                    "four" => Some('4'),
                    "five" => Some('5'),
                    "six" => Some('6'),
                    "seven" => Some('7'),
                    "eight" => Some('8'),
                    "nine" => Some('9'),
                    &_ => None }
            }
        }
    }
    value
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
    where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}