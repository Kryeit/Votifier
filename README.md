<p align="center">
  <img width="200" src="https://github.com/Kryeit/Votifier/blob/1.20.4/src/main/resources/assets/votifier/icon.png">
</p>

<h1 align="center">Votifier for Fabric<br>
	<a href="https://legacy.curseforge.com/minecraft/mc-mods/votifier-for-fabric/files"><img src="https://cf.way2muchnoise.eu/versions/votifier-for-fabric.svg" alt="Supported Versions"></a>
	<a href="https://github.com/Kryeit/Votifier/blob/1.20.1/LICENSE"><img src="https://img.shields.io/github/license/Kryeit/Votifiere?style=flat&color=900c3f" alt="License"></a>
	<a href="https://www.curseforge.com/minecraft/mc-mods/votifier-for-fabric"><img src="http://cf.way2muchnoise.eu/votifier-for-fabric.svg" alt="CF"></a>
    <a href="https://modrinth.com/mod/votifier-for-fabric"><img src="https://img.shields.io/modrinth/dt/votifier-for-fabric?logo=modrinth&label=&suffix=%20&style=flat&color=242629&labelColor=5ca424&logoColor=1c1c1c" alt="Modrinth"></a>
    <br><br>
</h1>

This mod adds a link between Voting Sites (PlanetMinecraft, etc).

This plugin records the votes made by players, and gives them a reward (runs a command).

It's a really simple mod:
- Creates public & private keys inside `/mods/votifier/` folder
- Creates a votifier.json inside `/config/votifier/` folder

This votifier.json is:
```json
{
  "host": "0.0.0.0",
  "port": "8192",
  "debug": false,
  "command-after-voting": "/give %player% diamond 1",
  "voting-link": "link"
}
```

To set up the Votifier:
1) Set "host" to your server IP, in most cases leave it as "0.0.0.0", it's the local IP.
2) Open your port 8192. If you are using a hosting service, you may need to open it in the control panel or open a support ticket.
3) Fill the configuration on the Voting site, adding the PUBLIC key, NOT the PRIVATE.
4) Test with [MineStatus Votifier Tester](https://minestatus.net/tools/votifier)