# Processo di sviluppo adottato

## Meeting ed iterazioni pianificate

## Modalità di divisione in itinere dei task

## Modalità di revisione dei task

## Strumenti utilizzati

Segue un elenco di strumenti ausiliari adottati per svolgere il progetto più agilmente e cercare di renderlo conforme a
determinati standard qualitativi.

### sbt

Scala Build Tool è uno strumento di compilazione open-source per progetti Scala e Java, rappresenta un'ottima
alternativa a Gradle, sopratutto per progetti interamente o principalmente sviluppati in Scala. Si è optato per questo
strumento in seguito alla decisione di utilizzare per quanto possibile strumenti affini al linguaggio Scala. Un'altra
ragione che motiva tale scelta è la voglia di cimentarsi didatticamente con uno strumento nuovo, mai utilizzato e nel
contesto più adatto al suo impiego.

### ScalaTest

[ScalaTest](https://www.scalatest.org/) è lo strumento di testing più popolare di Scala e offre una profonda integrazione con numerosi tool, tra i quali troviamo JUnit, Ant, Maven e sbt.  
Il design di ScalaTest permette di evolvere i test con l'evoluzione dell'applicazione, poiché è possibile estendere i test o comporli nel caso in cui si introducano nuove funzionalità.  

ScalaTest supporta diversi stili di test, ognuno dei quali è pensato per uno scopo differente; la guida propone di sceglierne almeno due:
- uno stile ha l'obiettivo di essere utilizzato per i test a basso livello, quindi gli unit test e i test di integrazione dei componenti. Lo stile può generico che viene proposto prende il nome di ``FlatSpec``, anche se non è l'unica alternativa prevista.
- il secondo stile è invece quello adibito ai test di alto livello, che descrivono il funzionamento del sistema e testano che tutto il sistema, ad un certo livello di astrazione, funzioni nel modo previsto. Lo stile più adatto a questo tipo di testing si chiama ``FeatureSpec``, poiché è molto leggibile e facile da comprendere.

### ScalaStyle

ScalaStyle è uno strumento per esaminare codice Scala che permette di evidenziare potenziali problemi e mettere lo
sviluppatore in grado di risolverli in modo tempestivo facendo si che non si propaghino, inoltre il suo impiego perfette
di uniformare il codice alle convenzioni del linguaggio. Il suo funzionamento è subordinanto ad un file di
configurazione ``scalastyle_config.xml`` con indicate tutte le regole autoimposte da seguire per standardizzare la
stesura di codice. Gli eventuali problemi verrano segnalati allo sviluppatore sotto forma di warning.

### ScalaFix
[ScalaFix](https://github.com/scalacenter/scalafix) è uno strumento orientato al linting ed al refactor automatico del codice.\
Il tool si basa sulla definizione di regole; quest'ultime vengono applicate a tutti i sorgenti Scala del progetto alla ricerca di segmenti di codice che le violino. Quando tali violazioni vengono individuate, viene lanciato un messaggio di warning (per segnalarne la presenza allo sviluppatore) e il sorgente viene automaticamente corretto applicando la regola.\
Esistono possibili regole applicabili fornite nativamente dal tool, oltre che alcune definite dalla community (questo approccio favorisce la standardizzazione). È inoltre possibile definire nuove regole custom.\
Una volta specificato il plugin sbt, è possibile lanciare una singola regola tramite il comando 
  ```bash
sbt scalafix [rule name]
  ```
Alternativamente, è possibile elencare le regole che si desidera eseguire all'interno del file ``.scalafix.conf``; una volta predisposto tale file sarà semplicemente necessario lanciare il comando
  ```bash
sbt scalafix
  ```
per eseguire tutti i comandi in sequenza.

### Scoverage

Scoverage è un software che permette di effettuare verifiche sulla coverage in ambiente Scala. Per coverage si intende
la percentuale di codice attraversato dai test rispetto al totale della code base. Il plug-in da noi utilizzato
è [sbt-scoverage](https://github.com/scoverage/sbt-scoverage). Per configurarlo è stato necessario impostare i seguenti
parametri nel file di build:

  ```scala
coverageEnabled := true
coverageMinimum := 60 //%
coverageFailOnMinimum := true
coverageHighlighting := true
  ```

Parametro particolarmente importante è ``converageFailOnMinimum``, utilizzato anche nella pipeline della
[GitHub Action](#github-actions).

### CPD (PMD)

Lo strumento di tipo Copy-Paste Detection permette di verificare la presenza porzioni di codice ricorrente ed eventuali
ripetizioni, utile nell'applicazione del principio DRY (Don't Repeat Yourself). Supponendo che i blocchi di codice
duplicati svolgano gli stessi compiti o simili, qualsiasi refactoring, anche semplice, deve essere propagato in diverse
porzioni di codice. Il CPD è un componente di [PMD](https://pmd.github.io/latest/pmd_userdocs_cpd.html) che verrà
utilizzato al termine di ogni sprint (o ogni qualvolta sia necessario).

### GitHub Actions

Le GitHub Actions sono un meccaniscmo fornito da GitHub allo scopo di automatizare il workflow in continuous integration
e deployment. Permettono di effettuare Build, Test e Deploy, oltre che code review e branch management direttamente da
GitHub.  
Per configurare il workflow è stato utilizzato un plug-in chiamato
[sbt-github-actions](https://github.com/djspiewak/sbt-github-actions). Il plug-in permette di personalizzare e
arricchire un workflow standard che si limita a

- predisporre una macchina virtuale Linux (ubuntu-latest)
- scricare ed installare ambienti Java e Scala
- fare il checkout del progetto
- eseguire la build del progetto e relativi test
- se i test hanno esito positivo e ci si trova su master github actions gestisce anche le release. Il plug-in è stato da
  noi configurato in modo da rimuovere il processo di release ed introducendo nuovi controlli come la copertura minima
  di [Scoverage](#scoverage).
  ```scala
  // a publish job is not desired  
  ThisBuild / githubWorkflowPublishTargetBranches := Seq()

  // add the coverage job
  ThisBuild / githubWorkflowBuildPostamble ++= List(
  WorkflowStep.Sbt(List("coverageReport"), name = Some("Coverage")))
  ```

### Trello
[Trello](https://trello.com/it) è uno strumento online per la gestione di progetti e di task, estremamente utile per organizzare il lavoro tra più membri dello stesso team.  
Il software permette di costruire in modo molto personalizzabile una board, ovvero una lavagna sulla quale si possono segnare task e assegnarli a uno o più collaboratori. Fornisce anche numerosi supporti per la creazione di meccanismi automatizzati, di modo da non essere costretti a svolgere operazioni ripetitive.  
Essendo molto versatile, Trello è ideale per creare un ambiente su misura per applicare anche una metodologia di lavoro agile come Scrum.

### GitHub Pages

La documentazione del progetto è mantenuta tramite un processo di continuous integration grazie ad una feature proposta
da GitHub: GitHub Pages. GitHub Pages fornisce un modo per automatizzare la pubblicazione di pagine web da semplice
testo scritto in markdown, un linguaggio human-readable. La documentazione è mantenuta su un branch orfano denominato
``"/docs"``.

### Pull Requests

---