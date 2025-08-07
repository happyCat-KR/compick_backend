package kr.gg.compick.domain;

@Entity
@Table(name = "board")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    private String content;

    @Column(name = "del_check")
    private boolean delCheck;

    @ManyToOne
    @JoinColumn(name = "del_user_idx")
    private User delUser;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Board parent;

    private String alert1;
    private String alert2;
    private String alert3;
}
