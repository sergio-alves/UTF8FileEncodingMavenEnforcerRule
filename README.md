#UTF8EncodingEnforcer Rule

How to used it? Copy this block into your plugins section of your pom.

	<!-- The maven enforcer plugin to p*** people off :-) -->
	<plugin>
	    <groupId>org.apache.maven.plugins</groupId>
	    <artifactId>maven-enforcer-plugin</artifactId>
	    <version>1.4.1</version>
	    <dependencies>
	        <dependency>
	            <groupId>ch.santosalves.maven.plugin.enforcer.rule</groupId>
	            <artifactId>files-must-match-encoding</artifactId>
	            <version>0.0.1-SNAPSHOT</version>
	        </dependency>
	    </dependencies>
	    <executions>
	        <execution>
	            <id>enforce-versions</id>
	            <goals>
	                <goal>enforce</goal>
	            </goals>
	            <configuration>
	                <failFast>true</failFast>
	                <rules>
	                    <fileEncodingEnforcerRule implementation="ch.santosalves.maven.plugin.enforcer.rule.UTF8EncodingEnforcerRule">
                           <dirToScan>${file.encoding.enforcer.rule.dir.to.scan}</dirToScan>
                       </fileEncodingEnforcerRule>
	                </rules>
	            </configuration>
	        </execution>
	    </executions>
	</plugin>