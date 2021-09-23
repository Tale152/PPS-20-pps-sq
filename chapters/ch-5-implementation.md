# Implementazione

## Utilizzo del paradigma funzionale

## Utilizzo del paradigma logico

## Test

L’utilizzo di test si è rivelato fondamentale per la corretta realizzazione del progetto. Si è cercato di scrivere test che
premettessero di garantire qualità del sistema realizzato e che fossero anche utilizzabili come documentazione dello stesso. 

### Metodologie di testing

#### Test Automatizzati
Per quanto riguarda il testing automatizzato si è cercato si seguire un approccio quanto più possibile TDD (Test Driven Development), cercando di seguire il ciclo Red - Green - Refactor. Questo è stato possibile soprattuto nelle fasi inziali del progetto, dove la maggior parte delle classi sviluppate appartenevano al model.  
Nelle fasi successive questo approccio è venuto meno. Diversi test sono stati realizzati seguendo un approccio più tradizionale, meno stringente di quanto richiesto normalmente dalle politiche del TDD.  
Inoltre alcuni componenti del sistema sono risultati particolarmente tediosi da testare in modo automatizzato a causa dell'incapsulamento dello stato, il quale non è spesso visibile dall'esterno. Lo strumento utilizzato per effettuare il testing è [ScalaTest](#scalatest).
In particolare è stata utilizzata la specifica _FlatSpec_ per la maggior parte dei test.

##### Suites di Testing
Tra le funzionalità di ScalaTest utilizzate vi sono le [Suites](https://www.scalatest.org/scaladoc/1.8/org/scalatest/Suites.html).
Ogni classe di Test creata è effettivamente già una Suite di test.
È possibile però creare classi che estendono Suites, specificando come argomenti altre Suite.
Questo permette non solo una migliore separazione dei concetti, ma in determinati casi permette di limitare la duplicazione di codice (applicando il principio DRY).  
La classe di Test _FileSystemSuite_ per esempio esegue due suite di test che hanno una cosa in comune: entrambe hanno bisogno di una cartella all'interno del file system per creare e manipolare file.  
Grazie alla trait _BeforeAndAfterAll_ è possibile creare una cartella dedicata a questi test nella directory di Temp della macchina che li esegue e cancellarla una volta terminata l'operazione.
##### Tags
I [Tag](https://www.scalatest.org/user_guide/tagging_your_tests) sono un'altra funzionalità di ScalaTest che permette di assegnare un tag (un'etichetta) a determinati test. Questo permette non solo una migliore catalogazione, ma anche di eseguire (o ignorare) determinati test utilizzando comandi specifici.  
Questa funzione è stata molto utile per impedire a determinati test di venir eseguiti tramite GitHub Action, in quanto alcuni test hanno necessità di renderizzare View o caricare e riprodurre suoni.  
Alcuni test, funzionanti su macchine locali, se eseguiti tramite GitHub actions erano infatti causa di  eccezioni per colpa di limitazione imposte dall'ambiente virtuale.

##### Coverage
È stato utilizzato lo strumento Scoverage per verificare la percentuale di codice attraversato dai test rispetto al totale della code base.  
Come illustrato [precedentemente](#test-automatizzati) non è stato possibile automatizzare i test per ogni componente del sistema, quindi sono stati esclusi alcuni file sorgenti e alcuni package dall'analisi della coverage.

#### Test non Automatizzati
Per alcuni componenti del sistema non è stato possibile creare test automatizzati. Sono comunque state realizzate delle sessioni di testing in cui si è scandagliato il sistema in funzione per cercare bug, inesattezze o comportamenti non attesi.  
Durante queste sessioni, verificatesi soprattuto durante gli sprint finali, si è cercato di portare il sistema in molti casi limite per verificare che il comportamento effettivo fosse anche quello previsto.

## Suddivisione del lavoro
<!-- NOTA: per ogni studente, una sotto-sezione descrittiva di cosa fatto/co-fatto e con chi, e descrizione di aspetti implementativi importanti non già presenti nel design)-->
### Filaseta Angelo
<!-- Lista di componenti -->
### Sanchi Piero
<!-- Lista di componenti -->
### Talmi Alessandro
<!-- Lista di componenti -->
### Tronetti Elisa
<!-- Lista di componenti -->

---
