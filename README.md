
# P2P-Chat
## Anforderungen
- Chat-Anwendung :white_check_mark:
- Clients kommunizieren P2P ohne Server :white_check_mark:
- Verbindung direkt über IP/Port :white_check_mark:
- Umsetzung in Java :white_check_mark:
- Minimal: Im LAN in Konsole. :white_check_mark:
- Optional: Server zur Verteilung der IP-Adressen der verbundenen Clients :white_check_mark:
- Optional: Alle lokalen Clients finden, wie mDNSresponder

**Aufwandsschätzung**: Range: 6h-32h, Mittel: 16h-24h
## Nutzung
### Allgemein
Im Client können über die Konsole die unten aufgeführten Befehle ausgeführt werden. Bei unvollständiger oder falscher Eingabe wird eine Hilfe angezeigt. Auf jede Eingabe erfolgt eine Rückmeldung (Success / Error).

**Ablauf der Verbindung**:
1. Verbindung mit dem Server aufbauen (SERVER CONNECT `IP` `Port` `Username`)
2. Abfragen der verbundenen Clients (SERVER LISTUSERS)
3. Auswählen des gewünschten Clients, Verbindung und Chatbeginn mit Hilfe der UUID (CLIENT CONNECT `UUID` ; CLIENT CHAT `UUID`)
### Befehle
SERVER CONNECT `IP` `Port` `Username`  
SERVER AUTOCONNECT `Username`  
SERVER DISCONNECT  
SERVER STATUS  
SERVER LISTUSERS  

CLIENT CONNECT `UUID`  
CLIENT CHAT `UUID`  
## Code Quality
### Unit tests
Im Sourcefolder /test enthalten.
### Statische Codeanalyse
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/eff6c445838342ac9466d8288060e00b)](https://app.codacy.com/gh/PatrickB2109/p2pchat?utm_source=github.com&utm_medium=referral&utm_content=PatrickB2109/p2pchat&utm_campaign=Badge_Grade_Settings)
