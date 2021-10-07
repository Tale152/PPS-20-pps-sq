# 1 - Processo di sviluppo adottato

## 1.1 - Meeting e iterazioni pianificate
#### 1.1.1 - Meeting Giornalieri
Date le disponibilità orarie molto divergenti tra i componenti del team, realizzare meeting giornalieri non è stato praticabile e si è optato per un approccio diverso.  

Grazie allo strumento Trello è stato realizzato un sistema per gestire la divisione dei task.  
Nel caso un componente del team desiderasse essere aggiornato sullo stato del progetto, egli poteva farlo accedendo alla dashboard di Trello per verificare quali cambiamenti fossero nel frattempo subentrati.  

#### 1.1.2 - Meeting decadàli
Ogni dieci giorni (ovvero alla fine di ogni Sprint), si è scelto di tenere un meeting in cui vengono definiti i task da includere nella fase di Sprint successiva.   

Le interazioni tra i componenti del team si sono mantenute comunque frequenti (tramite servizi di messaggistica istantanea e videochiamate) per convenire su eventuali dettagli di minore entità all’interno del progetto.

## 1.2 - Modalità di divisione in itinere dei task
La divisione dei Task tra i componenti del gruppo è avvenuta durante il meeting che si tiene all'inizio di ogni sprint.  

In ogni meeting:
 - Sono stati prima definiti i requisti e gli obiettivi di ogni sprint;
 - Sono stati creati _n_ task, possibilmente minimizzando al minimo le dipendenze;
 - Gli _n_ task sono stati divisi tra i componenti del gruppo in modo equo.

È quindi stato impossibile definire a priori i task assegnati ad ogni componente del gruppo.  
I task sono comunque stati ripartiti in modo che ogni membro fosse responsabile di una sua parte di design di dettaglio e implementazione.

## 1.3 - Modalità di revisione dei task
Alla fine di ogni sprint, ciascun componente ha descritto brevemente a tutti gli altri il suo lavoro.  
Sulla base del risultato generale i task sono stati quindi nuovamente introdotti se non terminati ed è quindi stata corretta la tabella di marcia.  

Nello sviluppo del progetto sono state comunque previste diverse fasi di revisione.  
Ciascun membro del gruppo si è occupato di revisionare e ri-fattorizzare il proprio codice in maniera autonoma.  
Grazie al meccanismo delle [Pull Requests](#pull-requests), un componente diverso da chi ha scritto il sorgente sottoposto è stato in grado di effettuare code review e verificare che non ci fossero problemi critici, diminuendo in questo modo anche il potenziale debito tecnico.

## 1.4 - Strumenti utilizzati
Segue un elenco di strumenti ausiliari adottati per svolgere il progetto più agilmente e renderlo conforme a determinati standard qualitativi.

### 1.4.1 - sbt
[Scala Build Tool](https://www.scala-sbt.org/) è uno strumento di compilazione open-source per progetti Scala e Java; rappresenta un'ottima alternativa a Gradle, sopratutto per progetti interamente o principalmente sviluppati in Scala.  

Si è optato per questo strumento in seguito alla decisione di utilizzare per quanto possibile strumenti affini al linguaggio Scala.  
Inoltre, un'altra ragione che motiva tale scelta, è la voglia di cimentarsi didatticamente con uno strumento nuovo, mai utilizzato e nel contesto più adatto al suo impiego.

### 1.4.2 - ScalaTest
[ScalaTest](https://www.scalatest.org/) è lo strumento di testing più popolare per il linguaggio Scala e offre una profonda integrazione con numerosi tool, tra i quali troviamo JUnit, Ant, Maven e sbt.  
Il design di ScalaTest permette di evolvere i test di pari passo con l'applicazione, poiché è possibile estendere i test o comporli nel caso in cui si introducano nuove funzionalità.  

ScalaTest supporta diversi stili di test, ognuno dei quali è pensato per uno scopo differente; la guida propone di sceglierne almeno due:
- Uno stile ha l'obiettivo di essere utilizzato per i test di basso livello (gli unit test e i test di integrazione dei componenti). Lo stile più generico che viene proposto prende il nome di ``FlatSpec``, anche se non è l'unica alternativa prevista.
- Il secondo stile è invece quello adibito ai test di alto livello, che descrivono il funzionamento del sistema e testano che tutto il sistema, ad un certo livello di astrazione, funzioni nel modo previsto. Lo stile più adatto a questo tipo di testing si chiama ``FeatureSpec``, poiché è molto leggibile e facile da comprendere.

### 1.4.3 - ScalaStyle
[ScalaStyle](http://www.scalastyle.org/) è uno strumento per esaminare codice Scala che permette di evidenziare potenziali problemi permettendo allo sviluppatore di risolverli in modo tempestivo facendo sì che non si propaghino; il suo impiego inoltre permette di uniformare il codice alle convenzioni del linguaggio.  

Il suo funzionamento è subordinanto ad un file di configurazione ``scalastyle_config.xml``; tale file indica tutte le regole autoimposte da seguire per standardizzare la stesura di codice.  
Eventuali problemi verrano segnalati allo sviluppatore sotto forma di warning.

### 1.4.4 - ScalaFix
[ScalaFix](https://github.com/scalacenter/scalafix) è uno strumento orientato al linting e al refactor automatico del codice.  
Il tool si basa sulla definizione di regole; quest'ultime vengono applicate a tutti i sorgenti Scala del progetto alla ricerca di segmenti di codice che le violino. Quando tali violazioni vengono individuate, viene lanciato un messaggio di warning (per segnalarne la presenza allo sviluppatore) e il sorgente viene automaticamente corretto applicando la regola.  

Esistono possibili regole applicabili fornite nativamente dal tool, oltre che alcune definite dalla community (questo approccio favorisce la standardizzazione). È inoltre possibile definire nuove regole custom.  

Una volta specificato il plugin sbt, è possibile lanciare una singola regola tramite il comando 
  ```bash
sbt scalafix [rule name]
  ```
Alternativamente, è possibile elencare le regole che si desidera eseguire all'interno del file ``.scalafix.conf``; una volta predisposto tale file sarà semplicemente necessario lanciare il comando
  ```bash
sbt scalafix
  ```
per eseguire tutti i comandi in sequenza.

### 1.4.5 - Scoverage
Scoverage è un software che permette di effettuare verifiche sulla coverage in ambiente Scala.  
Per coverage si intende la percentuale di codice attraversato dai test rispetto al totale della code base.  

Il plug-in da noi utilizzato è [sbt-scoverage](https://github.com/scoverage/sbt-scoverage).  
Per configurarlo è stato necessario impostare i seguenti parametri nel file di build:

  ```scala
coverageEnabled := true
coverageMinimum := 60 //%
coverageFailOnMinimum := true
coverageHighlighting := true
  ```

Parametro particolarmente importante è ``converageFailOnMinimum``, utilizzato anche nella pipeline della
[GitHub Action](#github-actions).

### 1.4.6 - CPD (PMD)
Lo strumento, di tipo Copy-Paste Detection, permette di verificare la presenza porzioni di codice ricorrente ed eventuali ripetizioni, utile nell'applicazione del principio DRY (Don't Repeat Yourself).  
Supponendo che i blocchi di codice duplicati svolgano gli stessi compiti o simili, qualsiasi refactoring, anche semplice, deve essere propagato in diverse porzioni di codice.  

Il CPD è un componente di [PMD](https://pmd.github.io/latest/pmd_userdocs_cpd.html) che verrà utilizzato al termine di ogni sprint (o ogni qualvolta sia necessario).

### 1.4.7 - GitHub Actions
Le GitHub Actions sono un meccanismo fornito da GitHub allo scopo di automatizare il workflow in continuous integration e deployment.  
Permettono di effettuare Build, Test e Deploy, oltre che code review e branch management direttamente da GitHub.  

Per configurare il workflow è stato utilizzato un plug-in chiamato
[sbt-github-actions](https://github.com/djspiewak/sbt-github-actions).  
Il plug-in permette di personalizzare e arricchire un workflow standard che si limita a:
- predisporre una macchina virtuale Linux (ubuntu-latest)
- scricare ed installare ambienti Java e Scala
- fare il checkout del progetto
- eseguire la build del progetto e relativi test
- se i test hanno esito positivo e ci si trova su master github actions gestisce anche le release. Il plug-in è stato da noi configurato in modo da rimuovere il processo di release e introducendo nuovi controlli come la copertura minima di [Scoverage](#scoverage).  

  ```scala
  // a publish job is not desired  
  ThisBuild / githubWorkflowPublishTargetBranches := Seq()

  // add the coverage job
  ThisBuild / githubWorkflowBuildPostamble ++= List(
  WorkflowStep.Sbt(List("coverageReport"), name = Some("Coverage")))
  ```

### 1.4.8 - Trello
[Trello](https://trello.com/it) è uno strumento online per la gestione di progetti e di task, estremamente utile per organizzare il lavoro tra più membri dello stesso team.  

Il software permette di costruire in modo molto personalizzabile una board, ovvero una lavagna sulla quale si possono segnare task e assegnarli a uno o più collaboratori. Inoltre ogni task aggiunto può essere contrassegnato da una o più label, di modo da rendere immediatamente chiaro quali sono i task con maggiore priorità e a quale ambito del progetto fanno riferimento.  

Trello fornisce anche numerosi supporti per la creazione di meccanismi automatizzati, di modo da non essere costretti a svolgere operazioni ripetitive. Sono stati sfruttati per esempio per ripristinare l'ambiente all'inizio di ogni sprint, spostando automaticamente tutti i task completati in una nuova colonna dedicata.  

Essendo molto versatile, Trello è ideale per creare un ambiente su misura per applicare anche una metodologia di lavoro agile come Scrum.

### 1.4.9 - GitHub Pages
La documentazione del progetto è mantenuta tramite un processo di continuous integration grazie ad una feature proposta da GitHub: [GitHub Pages](https://pages.github.com/).  
GitHub Pages fornisce un modo per automatizzare la pubblicazione di pagine web da semplice testo scritto in markdown a un linguaggio human-readable. La documentazione è mantenuta su un branch orfano denominato ``"/docs"``.

### 1.4.10 - Pull Requests
Le pull request sono un meccanismo offerto da Git per gestire in modo ordinato il flusso di lavoro all'interno di un repository e favorire la collaborazione tra gli sviluppatori.  

Un developer che voglia collaborare allo sviluppo dovrà innanzitutto creare una propria fork.  
Una fork, in poche parole, è una copia del repository originale, ma di proprietà dello sviluppatore che ha effettuato la duplicazione.  
Il developer dunque lavora nella propria fork sviluppando una feature e, una volta terminata, effettua una pull request verso la repository originale; tale azione non ha risoluzione immediata, ma viene messa in attesa dell'approvazione da parte del mantainer della repository.  

Una pull request offre dunque la possibilità a tutti i collaboratori di discutere della feature sviluppata, fornendo un forum dedicato e strumenti utili alla code review.  
Il mantainer dunque potrà richiedere che il codice sottoposto subisca dei cambiamenti e, eventualmente, accettare o rifiutare la pull request.
In caso di accettazione i cambiamenti realizzati nella fork verranno integrati nel repository originale.  

Tutte le fork associate al repository potranno dunque sincronizzarsi ricevendo la nuova feature implementata.

---
