# wilma-cli
Kotlin cli app for [Wilma](https://wilma.fi/).

## Commands
| Command                                | Description              |
|----------------------------------------|--------------------------|
| help                                   | Shows help               |
| server [set/connect/disconnect/status] | Logins to Wilma          |
| debug                                  | Enables debug mode       |
| announcements                          | Shows announcements      |
| courses [current/past]                 | Shows courses            |
| exams [past/upcoming]                  | Shows exams              |
| info                                   | Shows about your session |
| timetable [default/year/custom]        | Shows timetable          |
| quit                                   | Quits                    |

## Start
First time you run the app, you need to set the server. You can do this by running `server set <url>`. 
Otherwise, the app will use the default demo server.

-----------------------------
## License
> [This source code is under the GNU General Public License, version 3.](https://www.gnu.org/licenses/gpl-3.0.txt)