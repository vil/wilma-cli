# wilma-cli
Kotlin cli app for [Wilma](https://wilma.fi/).

## Commands
| Command                                | Description                             |
|----------------------------------------|-----------------------------------------|
| help                                   | Shows help                              |
| server [set/connect/disconnect/status] | Connect/configure the Wilma server      |
| debug                                  | Enables debug mode                      |
| announcements                          | Shows announcements                     |
| courses [current/past]                 | Shows courses                           |
| exams [upcoming/past]                  | Shows exams                             |
| info                                   | Shows info about your session           |
| timetable [default/year/custom]        | Shows timetable                         |
| quit                                   | Quits                                   |

## Setup
### Install
1. Clone the repository `git clone https://github.com/V1li/wilma-cli.git`
2. Build the app `./gradlew build` or `gradlew.bat build`.
3. Copy the jar file from `build/libs` to your desired location.
4. Run the app `java -jar wilma-cli-<version>.jar`.

### Start
First time you run the app, you need to set the server. You can do this by running `server set <url>`. 
Otherwise, the app will use the default demo server.

-----------------------------
## License
> [This source code is under the GNU General Public License, version 3.](https://www.gnu.org/licenses/gpl-3.0.txt)
